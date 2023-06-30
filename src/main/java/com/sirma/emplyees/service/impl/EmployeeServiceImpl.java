package com.sirma.emplyees.service.impl;

import com.sirma.emplyees.exception.NoCommonProjectsException;
import com.sirma.emplyees.model.EmployeeProject;
import com.sirma.emplyees.model.EmployeeProjectResponse;
import com.sirma.emplyees.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Override
    public EmployeeProjectResponse findLongestWorkingPair(List<EmployeeProject> employeeProjects) {
        Optional<Map.Entry<String, Set<LocalDate>>> mostDaysWorkedTogetherPair = calculateTotalDaysWorked(employeeProjects).entrySet().stream()
                .max(Comparator.comparingLong(entry -> entry.getValue().size()));

        if (mostDaysWorkedTogetherPair.isPresent()) {
            String[] employeeIds = mostDaysWorkedTogetherPair.get().getKey().split("-");

            return new EmployeeProjectResponse(employeeIds[0], employeeIds[1], mostDaysWorkedTogetherPair.get().getValue().size());
        } else {
            throw new NoCommonProjectsException();
        }
    }

    private Map<String, Set<LocalDate>> calculateTotalDaysWorked(List<EmployeeProject> employeeProjects) {
        Map<String, Set<LocalDate>> daysWorkedTogetherMap = new HashMap<>();

        for (int i = 0; i < employeeProjects.size(); i++) {
            EmployeeProject employeeProject1 = employeeProjects.get(i);

            for (int j = i + 1; j < employeeProjects.size(); j++) {
                EmployeeProject employeeProject2 = employeeProjects.get(j);

                if (Objects.equals(employeeProject1.getProjectId(), employeeProject2.getProjectId())) {
                    String empId1 = employeeProject1.getEmpId();
                    String empId2 = employeeProject2.getEmpId();
                    LocalDate dateFrom1 = employeeProject1.getDateFrom();
                    LocalDate dateFrom2 = employeeProject2.getDateFrom();
                    LocalDate dateTo1 = employeeProject1.getDateTo();
                    LocalDate dateTo2 = employeeProject2.getDateTo();

                    try {
                        if (dateTo1 == null) {
                            dateTo1 = LocalDate.now();
                        }
                        if (dateTo2 == null) {
                            dateTo2 = LocalDate.now();
                        }
                        if (dateFrom1.isAfter(dateTo2) || dateFrom2.isAfter(dateTo1)) {
                            throw new NoCommonProjectsException();
                        }

                        LocalDate commonStartDate = dateFrom1.isAfter(dateFrom2) ? dateFrom1 : dateFrom2;
                        LocalDate commonEndDate = dateTo1.isBefore(dateTo2) ? dateTo1 : dateTo2;

                        String key = generateKey(empId1, empId2);
                        Set<LocalDate> daysWorkedTogetherValue = daysWorkedTogetherMap.getOrDefault(key, new HashSet<>());
                        for (LocalDate date = commonStartDate; !date.isAfter(commonEndDate); date = date.plusDays(1)) {
                            daysWorkedTogetherValue.add(date);
                        }
                        daysWorkedTogetherMap.put(key, daysWorkedTogetherValue);
                    } catch (NoCommonProjectsException ex) {
                        log.info(ex.getMessage());
                    }

                }
            }
        }

        return daysWorkedTogetherMap;
    }

    private String generateKey(String empId1, String empId2) {
        return empId1.compareTo(empId2) < 0 ? empId1 + "-" + empId2 : empId2 + "-" + empId1;
    }
}
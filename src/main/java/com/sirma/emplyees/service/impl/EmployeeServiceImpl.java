package com.sirma.emplyees.service.impl;

import com.sirma.emplyees.exception.NoCommonProjectsException;
import com.sirma.emplyees.model.EmployeeProject;
import com.sirma.emplyees.model.EmployeeProjectResponse;
import com.sirma.emplyees.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public EmployeeProjectResponse findLongestWorkingPair(List<EmployeeProject> employeeProjects) {
        Map<String, EmployeeProjectResponse> map = new HashMap<>();

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
                        long daysWorked = Duration.between(commonStartDate.atStartOfDay(), commonEndDate.atStartOfDay()).toDays();

                        String key = empId1 + "-" + empId2;
                        EmployeeProjectResponse existingPair = map.get(key);
                        if (existingPair == null || daysWorked > existingPair.getDaysWorkedTogether()) {
                            map.put(key, new EmployeeProjectResponse(empId1, empId2, daysWorked));
                        }
                    } catch (NoCommonProjectsException ex) {
                        log.info(ex.getMessage());
                    }
                }
            }
        }

        Optional<EmployeeProjectResponse> longestWorkingPair = map.values().stream().max(Comparator.comparingLong(EmployeeProjectResponse::getDaysWorkedTogether));

        return longestWorkingPair.orElseThrow(NoCommonProjectsException::new);
    }
}
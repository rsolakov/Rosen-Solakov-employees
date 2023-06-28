package com.sirma.emplyees.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.sirma.emplyees.exception.UnsupportedFileFormatException;
import com.sirma.emplyees.model.EmployeeProject;
import com.sirma.emplyees.service.DataLoaderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataLoaderServiceImpl implements DataLoaderService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final ObjectMapper objectMapper;

    @Override
    public List<EmployeeProject> loadDataFromFile(InputStream inputStream, String fileName) throws IOException {
        String fileExtension = getFileExtension(fileName);

        return switch (fileExtension) {
            case "csv" -> loadDataFromCSV(inputStream);
            case "json" -> loadDataFromJSON(inputStream);
            default -> throw new UnsupportedFileFormatException(fileExtension);
        };
    }

    private List<EmployeeProject> loadDataFromJSON(InputStream inputStream) throws IOException {
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(List.class, EmployeeProject.class);
        return objectMapper.readValue(inputStream, listType);
    }

    private List<EmployeeProject> loadDataFromCSV(InputStream inputStream) {
        List<EmployeeProject> employeeProjects = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream))) {
            String[] header = csvReader.readNext();
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                String empId = line[0].trim();
                String projectId = line[1].trim();
                var dateFrom = LocalDate.parse(line[2].trim(), DATE_FORMATTER);
                var dateTo = parseDateTo(line[3].trim());

                var employeeProject = mapEmployeeProject(empId, projectId, dateFrom, dateTo);
                employeeProjects.add(employeeProject);
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return employeeProjects;
    }

    private static EmployeeProject mapEmployeeProject(String empId, String projectId, LocalDate dateFrom, LocalDate dateTo) {
        var employeeProject = new EmployeeProject();
        employeeProject.setEmpId(empId);
        employeeProject.setProjectId(projectId);
        employeeProject.setDateFrom(dateFrom);
        employeeProject.setDateTo(dateTo);
        return employeeProject;
    }

    private LocalDate parseDateTo(String dateString) {
        if (dateString.equalsIgnoreCase("NULL")) {
            return null;
        } else {
            return LocalDate.parse(dateString.trim(), DATE_FORMATTER);
        }
    }

    private String getFileExtension(String filePath) {
        int dotIndex = filePath.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < filePath.length() - 1) {
            return filePath.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }
}

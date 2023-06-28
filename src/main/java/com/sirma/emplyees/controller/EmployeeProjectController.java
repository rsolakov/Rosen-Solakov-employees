package com.sirma.emplyees.controller;

import com.sirma.emplyees.model.EmployeeProject;
import com.sirma.emplyees.model.EmployeeProjectResponse;
import com.sirma.emplyees.service.DataLoaderService;
import com.sirma.emplyees.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * Controller class that handles EmployeeProject-related endpoints.
 * Provides RESTful APIs for uploading a file and retrieving the longest working pair of employees.
 * All endpoints are mapped under the "/employees" base path.
 */
@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeProjectController {

    private final EmployeeService employeeService;
    private final DataLoaderService dataLoaderService;

    /**
     * Uploads a file containing EmployeeProject data and retrieves the longest working pair of employees.
     *
     * @param file the MultipartFile representing the uploaded file.
     * @return the EmployeeProjectResponse representing the longest working pair of employees. It holds employeeId1, employeeId2 and daysWorkedTogether.
     * @throws IOException if an I/O error occurs while reading the uploaded file.
     */
    @PostMapping(value = "/load-file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public EmployeeProjectResponse uploadFile(@RequestPart("file") MultipartFile file) throws IOException {
        List<EmployeeProject> employeeProject = dataLoaderService.loadDataFromFile(file.getInputStream(), file.getOriginalFilename());
        return employeeService.findLongestWorkingPair(employeeProject);
    }
}

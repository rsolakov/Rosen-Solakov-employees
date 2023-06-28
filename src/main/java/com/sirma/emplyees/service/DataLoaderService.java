package com.sirma.emplyees.service;

import com.sirma.emplyees.model.EmployeeProject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface DataLoaderService {

    /**
     * Loads EmployeeProject data from a file specified by the input stream and file name.
     *
     * @param inputStream the input stream containing the employee project data.
     * @param fileName the name of the file to load.
     * @return List of EmployeeProjects. EmployeeProject represent the loaded data and holds empId, projectId, dateFrom, dateTo.
     * @throws IOException if an I/O error occurs while reading the input stream.
     */
    List<EmployeeProject> loadDataFromFile(InputStream inputStream, String fileName) throws IOException;
}

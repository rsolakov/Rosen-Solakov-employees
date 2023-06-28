package com.sirma.emplyees.service;

import com.sirma.emplyees.model.EmployeeProject;
import com.sirma.emplyees.model.EmployeeProjectResponse;

import java.util.List;

public interface EmployeeService {

   /**
    * This method is for finding the longest period of days that employees have worked together on a common project.
    *
    * @param employeeProjects List of employeeProjects. EmployeeProject holds empId, projectId, dateFrom, dateTo.
    * @return the EmployeeProjectResponse representing the longest working pair of employees. It holds employeeId1, employeeId2 and daysWorkedTogether.
    */
   EmployeeProjectResponse findLongestWorkingPair(List<EmployeeProject> employeeProjects);
}

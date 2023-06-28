package com.sirma.emplyees.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class EmployeeProjectResponse {

    private String employeeId1;
    private String employeeId2;
    private long daysWorkedTogether;
}

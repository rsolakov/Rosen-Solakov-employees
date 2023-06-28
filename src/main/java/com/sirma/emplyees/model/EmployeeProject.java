package com.sirma.emplyees.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@RequiredArgsConstructor
@Getter
@Setter
@ToString
public class EmployeeProject {

    private String empId;
    private String projectId;
    @NotNull
    private LocalDate dateFrom;
    private LocalDate dateTo;
}

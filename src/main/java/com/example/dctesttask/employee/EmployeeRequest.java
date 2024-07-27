package com.example.dctesttask.employee;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class EmployeeRequest {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotBlank(message = "Role is mandatory")
    private String role;
    @Positive(message = "Salary is mandatory")
    private int salary;
    @NotNull(message = "Department id is mandatory")
    private Long departmentId;
}

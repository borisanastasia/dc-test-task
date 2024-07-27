package com.example.dctesttask.employee;

import lombok.Data;

@Data
public class EmployeeRequest {
    private String name;
    private String role;
    private int salary;
    private Long departmentId;
}

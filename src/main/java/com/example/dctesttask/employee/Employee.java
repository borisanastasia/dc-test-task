package com.example.dctesttask.employee;

import com.example.dctesttask.department.Department;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String role;
    private int salary;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}

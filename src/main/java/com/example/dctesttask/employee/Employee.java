package com.example.dctesttask.employee;

import com.example.dctesttask.department.Department;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {

    @Id
    @SequenceGenerator(name = "employee_id_seq", sequenceName = "employee_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id_seq")
    private Long id;
    private String name;
    private String role;
    private int salary;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
}

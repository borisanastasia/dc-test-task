package com.example.dctesttask;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String role;
    private int salary;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
}

package com.example.dctesttask.employee;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    boolean existsByDepartmentId(Long departmentId);
}

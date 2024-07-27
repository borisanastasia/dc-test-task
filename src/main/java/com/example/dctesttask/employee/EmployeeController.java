package com.example.dctesttask.employee;

import com.example.dctesttask.department.Department;
import com.example.dctesttask.department.DepartmentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @GetMapping
    public Iterable<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Employee findById(@PathVariable Long id) {
        return verifyEmployee(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        employeeRepository.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Employee create(@Valid @RequestBody EmployeeRequest employeeRequest) {
        var department = verifyDepartment(employeeRequest.getDepartmentId());
        var employee = new Employee();

        employee.setName(employeeRequest.getName());
        employee.setRole(employeeRequest.getRole());
        employee.setSalary(employeeRequest.getSalary());
        employee.setDepartment(department);

        return employeeRepository.save(employee);
    }

    @PutMapping("/{id}")
    public Employee update(@PathVariable Long id, @Valid @RequestBody EmployeeRequest employeeRequest) {
        var employee = verifyEmployee(id);

        if (!employee.getDepartment().getId().equals(employeeRequest.getDepartmentId())) {
            var department = verifyDepartment(employeeRequest.getDepartmentId());
            employee.setDepartment(department);
        }
        employee.setName(employeeRequest.getName());
        employee.setRole(employeeRequest.getRole());
        employee.setSalary(employeeRequest.getSalary());

        return employeeRepository.save(employee);
    }

    private Employee verifyEmployee(Long id) {
        return employeeRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "There is no employee with id " + id));
    }

    private Department verifyDepartment(Long id) {
        return departmentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "There is no department with id " + id));
    }
}

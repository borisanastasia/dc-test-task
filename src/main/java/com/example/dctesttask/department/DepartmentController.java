package com.example.dctesttask.department;

import com.example.dctesttask.employee.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @GetMapping
    public Iterable<Department> findAll() {
        return departmentRepository.findAll();
    }

    @GetMapping("/{id}")
    public Department findById(@PathVariable Long id) {
        return verifyDepartment(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        if (employeeRepository.existsByDepartmentId(id)) {
            throw new ResponseStatusException(FORBIDDEN, "Can't delete department with employees attached");
        }

        departmentRepository.deleteById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Department create(@Valid @RequestBody Department department) {
        return departmentRepository.save(department);
    }

    @PutMapping("/{id}")
    public Department update(@PathVariable Long id, @Valid @RequestBody Department department) {
        var existingDep = verifyDepartment(id);

        existingDep.setName(department.getName());
        return departmentRepository.save(existingDep);
    }

    private Department verifyDepartment(Long id) {
        return departmentRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "There is no department with id " + id));
    }
}
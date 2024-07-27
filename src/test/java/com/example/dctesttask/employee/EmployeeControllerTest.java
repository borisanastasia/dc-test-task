package com.example.dctesttask.employee;

import com.example.dctesttask.department.Department;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.instancio.Select.field;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class EmployeeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    private Department department;

    @BeforeEach
    @SneakyThrows
    void beforeEach() {
        var department = Instancio.of(Department.class)
                .ignore(field(Department::getId))
                .create();
        var result = mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andReturn();

        this.department = objectMapper.readValue(result.getResponse().getContentAsString(), Department.class);
    }

    @Test
    @SneakyThrows
    void findAll() {
        var count = 3;
        for (int i = 0; i < count; i++) {
            createRandomEmployee();
        }

        mockMvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(count)));
    }

    @Test
    @SneakyThrows
    void findById() {
        var id = createRandomEmployee().getId();

        mockMvc.perform(get("/employees/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id), Long.class))
                .andExpect(jsonPath("$.department.id", is(department.getId()), Long.class));

    }

    @Test
    @SneakyThrows
    void deleteById() {
        var id = createRandomEmployee().getId();

        mockMvc.perform(delete("/employees/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void create() {
        var employeeRequest = Instancio.of(EmployeeRequest.class)
                .set(field(EmployeeRequest::getDepartmentId), department.getId())
                .create();

        mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", isA(Integer.class)))
                .andExpect(jsonPath("$.department.id", is(department.getId()), Long.class));

    }

    @Test
    @SneakyThrows
    void update() {
        var employee = createRandomEmployee();
        var employeeRequest = new EmployeeRequest();
        employeeRequest.setName("hello-world");
        employeeRequest.setSalary(12345);
        employeeRequest.setRole(employee.getRole());
        employeeRequest.setDepartmentId(employee.getDepartment().getId());

        mockMvc.perform(put("/employees/" + employee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(employeeRequest.getName())))
                .andExpect(jsonPath("$.salary", is(employeeRequest.getSalary())))
                .andExpect(jsonPath("$.department.id", is(employeeRequest.getDepartmentId()), Long.class));
    }

    @SneakyThrows
    private Employee createRandomEmployee() {
        var employeeRequest = Instancio.of(EmployeeRequest.class)
                .set(field(EmployeeRequest::getDepartmentId), department.getId())
                .create();
        var result = mockMvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employeeRequest)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), Employee.class);
    }
}
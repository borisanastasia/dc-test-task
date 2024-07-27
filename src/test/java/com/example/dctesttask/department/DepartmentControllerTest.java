package com.example.dctesttask.department;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.instancio.Select.field;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class DepartmentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @SneakyThrows
    void findAll() {
        var count = 3;
        for (int i = 0; i < count; i++) {
            createRandomDepartment();
        }

        mockMvc.perform(get("/departments"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", greaterThanOrEqualTo(count)));
    }

    @Test
    @SneakyThrows
    void findById() {
        var id = createRandomDepartment().getId();

        mockMvc.perform(get("/departments/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id), Long.class));

    }

    @Test
    @SneakyThrows
    void deleteById() {
        var id = createRandomDepartment().getId();

        mockMvc.perform(delete("/departments/" + id))
                .andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    void create() {
        var department = Instancio.of(Department.class)
                .ignore(field(Department::getId))
                .create();

        mockMvc.perform(post("/departments")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", isA(Integer.class)));

    }

    @Test
    @SneakyThrows
    void update() {
        var department = createRandomDepartment();

        department.setName("hello-world");

        mockMvc.perform(put("/departments/" + department.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(department.getName())));
    }

    @SneakyThrows
    private Department createRandomDepartment() {
        var department = Instancio.of(Department.class)
                .ignore(field(Department::getId))
                .create();
        var result = mockMvc.perform(post("/departments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(department)))
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), Department.class);
    }
}
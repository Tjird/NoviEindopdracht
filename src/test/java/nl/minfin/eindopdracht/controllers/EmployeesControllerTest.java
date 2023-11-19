package nl.minfin.eindopdracht.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.minfin.eindopdracht.dto.AuthDto;
import nl.minfin.eindopdracht.dto.EmployeeDto;
import nl.minfin.eindopdracht.objects.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.charset.Charset;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmployeesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    MvcResult result;
    String token;

    EmployeeDto employeeDto;

    @BeforeEach
    void setUp() throws Exception {

        AuthDto authDto = new AuthDto();
        authDto.username = "admin";
        authDto.password = "admin";

        result = mockMvc.perform(
                post("/auth").contentType(APPLICATION_JSON_UTF8).content(asJsonString(authDto))
        ).andReturn();

        token = result.getResponse().getHeader("Authorization");
    }

    @Test
    void getAllEmployees() throws Exception {
        mockMvc.perform(get("/api/employees").header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));
    }

    @Test
    void createEmployee() throws Exception {
        employeeDto = new EmployeeDto();
        employeeDto.username = "karel";
        employeeDto.password = "k@r3ll1!";
        employeeDto.fullName = "Karel Hansen";
        employeeDto.role = Role.MECHANIC;

        mockMvc.perform(post("/api/employees").header("Authorization", token).contentType(APPLICATION_JSON_UTF8).content(asJsonString(employeeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("employeeId").value(5))
                .andExpect(jsonPath("username").value(employeeDto.username))
                .andExpect(jsonPath("password").value(employeeDto.password))
                .andExpect(jsonPath("fullName").value(employeeDto.fullName))
                .andExpect(jsonPath("role").value(employeeDto.role.toString()));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
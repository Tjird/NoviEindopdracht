package nl.minfin.eindopdracht.controllers;

import nl.minfin.eindopdracht.objects.enums.Role;
import nl.minfin.eindopdracht.objects.enums.Roles;
import nl.minfin.eindopdracht.objects.models.Employee;
import nl.minfin.eindopdracht.services.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(EmployeesController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin", authorities = {Roles.ADMIN})
class EmployeesControllerTest {

    private @Autowired MockMvc mockMvc;
    private @MockBean JwtService jwtService;
    private @MockBean JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "employees");
    }

    @Test
    void getAllEmployees() {

    }

    private List<Employee> initTest() {
        Employee emp1 = new Employee("username1", "pass1", "Tjeerd Rutte", Role.MECHANIC);
        Employee emp2 = new Employee("username2", "pass2", "Marc Rutte", Role.BACKOFFICE);
        Employee emp3 = new Employee("username3", "pass3", "Tjeerd Samson", Role.CASHIER);
        Employee emp4 = new Employee();
        Employee emp5 = new Employee();

        emp4.setUsername("username4");
        emp5.setRole(Role.ADMIN);

        return new ArrayList<>(List.of(emp1, emp2, emp3, emp4, emp5));
    }
}
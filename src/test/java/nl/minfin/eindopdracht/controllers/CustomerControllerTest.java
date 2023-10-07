package nl.minfin.eindopdracht.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.minfin.eindopdracht.objects.enums.Role;
import nl.minfin.eindopdracht.objects.enums.Roles;
import nl.minfin.eindopdracht.objects.models.Customer;
import nl.minfin.eindopdracht.objects.models.Employee;
import nl.minfin.eindopdracht.services.JwtService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.client.match.ContentRequestMatchers;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeesController.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", password = "admin", authorities = {Roles.ADMIN})
class CustomerControllerTest {
    public static final MediaType APPLICATION_JSON = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    final ObjectMapper objectMapper = new ObjectMapper();

    private @Autowired MockMvc mockMvc;
    private @MockBean JwtService jwtService;
    private @MockBean JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "customers");
    }

    @Test
    void createGetCustomerTest() throws JsonProcessingException {
        Customer cus = initTest().get(0);
        String jsonBody = objectMapper.writeValueAsString(cus);
        
        this.mockMvc.perform(post("/api/customers").contentType(APPLICATION_JSON).content(jsonBody))
                .andDo(print())
                .andExpect(status().isOk());
                
    }

    private List<Customer> initTest() {
        Customer cus1 = new Customer("Tjeerd Rutte", "0624680357", "A-123-BC");
        Customer cus2 = new Customer("Marc Rutte", "06000112345", "A-124-BC");
        Customer cus3 = new Customer("Piet Daal", "0634679432", "A-125-BC");
        Customer cus4 = new Customer();

        cus4.setTelephoneNumber("0687486927");

        return new ArrayList<>(List.of(cus1, cus2, cus3, cus4));
    }
}
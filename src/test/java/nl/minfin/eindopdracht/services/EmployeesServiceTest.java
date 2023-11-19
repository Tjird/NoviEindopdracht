package nl.minfin.eindopdracht.services;

import nl.minfin.eindopdracht.dto.EmployeeDto;
import nl.minfin.eindopdracht.objects.enums.Role;
import nl.minfin.eindopdracht.objects.models.Customer;
import nl.minfin.eindopdracht.objects.models.Employee;
import nl.minfin.eindopdracht.repositories.CustomerRepository;
import nl.minfin.eindopdracht.repositories.EmployeesRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeesServiceTest {

    @Mock
    EmployeesRepository employeesRepository;

    @InjectMocks
    EmployeesService employeesService;

    @Captor
    ArgumentCaptor<Employee> captor;

    Employee emp1;
    Employee emp2;
    Employee emp3;

    List<Employee> employeeList;

    @BeforeEach
    void setUp() {

        emp1 = new Employee(1L, "karel", "K@rell", "Karel de Kabouter", Role.ADMIN);
        emp2 = new Employee(2L, "peter", "Pe33tt", "Peter Pan", Role.MECHANIC);
        emp3 = new Employee(3L, "Henk", "H3nkil", "Henk de Tank", Role.CASHIER);

        employeeList = new ArrayList<>(List.of(emp1, emp2, emp3));
    }

    @Test
    void getListOfEmployees() {
        when(employeesRepository.findAll()).thenReturn(employeeList);

        List<Employee> empList = employeesService.getListOfEmployees();

        assertEquals(empList, employeeList);
    }

    @Test
    void createEmployee() {
        EmployeeDto employeeDto = new EmployeeDto();

        employeeDto.username = "niels";
        employeeDto.role = Role.CASHIER;
        employeeDto.fullName = "Niels Kopen";
        employeeDto.password = "N!lsK0pen";

        employeesService.createEmployee(employeeDto);
        verify(employeesRepository, times(1)).save(captor.capture());
        Employee employee1 = captor.getValue();

        assertEquals(employee1.getUsername(), employeeDto.username);
        assertEquals(employee1.getPassword(), employeeDto.password);
        assertEquals(employee1.getFullName(), employeeDto.fullName);
    }

    @Test
    void getEmployeeByName() {
        when(employeesRepository.findByUsername("karel")).thenReturn(emp1);

        Employee employee2 = employeesService.getEmployeeByName("karel");

        assertEquals(emp1.getFullName(), employee2.getFullName());
        assertEquals(emp1.getRole(), employee2.getRole());
    }

    @Test
    void getEmployeeById() {
        when(employeesRepository.findById(2L)).thenReturn(Optional.ofNullable(emp2));

        Optional<Employee> employee3 = employeesService.getEmployeeById(2L);

        assertEquals(employee3.get().getEmployeeId(), emp2.getEmployeeId());
    }
}
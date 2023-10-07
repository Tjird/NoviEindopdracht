package nl.minfin.eindopdracht.controllers;

import nl.minfin.eindopdracht.dto.CustomerDto;
import nl.minfin.eindopdracht.dto.EmployeeDto;
import nl.minfin.eindopdracht.objects.models.Customer;
import nl.minfin.eindopdracht.objects.models.Employee;
import nl.minfin.eindopdracht.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    // Endpoint om alle medewerkers weer te geven, deze is beveiligd voor alleen de ADMIN role
    @GetMapping()
    List<Employee> getAllEmployees() { return employeesService.getListOfEmployees(); }

    @PostMapping()
    Employee createEmployee(@RequestBody EmployeeDto newEmployee) {
        return employeesService.createEmployee(newEmployee);
    }


}

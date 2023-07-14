package nl.minfin.eindopdracht.controllers;

import nl.minfin.eindopdracht.objects.models.Employee;
import nl.minfin.eindopdracht.services.EmployeesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeesController {

    private @Autowired EmployeesService employeesService;

    // Endpoint om alle medewerkers weer te geven, deze is beveiligd voor alleen de ADMIN role
    @GetMapping()
    List<Employee> getAllEmployees() { return employeesService.getListOfEmployees(); }

}

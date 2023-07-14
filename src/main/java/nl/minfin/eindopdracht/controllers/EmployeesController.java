package nl.minfin.eindopdracht.controllers;

import nl.minfin.eindopdracht.objects.enums.Role;
import nl.minfin.eindopdracht.objects.enums.Roles;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/*
 * Behandelen van HTTP requests over de medewerkers.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeesController {

    // Returns all repairs.

    @Secured(Roles.ADMIN)
    @GetMapping()
    String getAllEmployees() {
        return "All employees";
    }

}

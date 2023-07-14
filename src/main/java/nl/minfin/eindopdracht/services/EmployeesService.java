package nl.minfin.eindopdracht.services;

import nl.minfin.eindopdracht.objects.enums.Role;
import nl.minfin.eindopdracht.objects.exceptions.EmployeeNotExistsException;
import nl.minfin.eindopdracht.objects.exceptions.ModifyAdminException;
import nl.minfin.eindopdracht.objects.exceptions.UsernameExistsException;
import nl.minfin.eindopdracht.objects.models.Employee;
import nl.minfin.eindopdracht.repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EmployeesService {

    private @Autowired EmployeesRepository employeeRepository;
    private @Autowired BCryptPasswordEncoder passwordEncoder;

    public Employee updateEmployee(Long employeeId, Employee newEmployee) {
        if (employeeRepository.findByUsername(newEmployee.getUsername()) != null) {
            throw new UsernameExistsException(newEmployee.getUsername());
        } else {
            return employeeRepository.findById(employeeId)
                    .map(employee -> {
                        if (employee.getRole() != Role.ADMIN && newEmployee.getRole() == Role.ADMIN) {
                            throw new ModifyAdminException("create");
                        } else if (employee.getRole() == Role.ADMIN && newEmployee.getRole() != Role.ADMIN) {
                            throw new ModifyAdminException("delete");
                        }

                        if (newEmployee.getUsername() != null) {
                            employee.setUsername(newEmployee.getUsername());
                        }
                        if (newEmployee.getPassword() != null) {
                            employee.setPassword(passwordEncoder.encode(newEmployee.getPassword()));
                        }
                        if (newEmployee.getFullName()!= null) {
                            employee.setFullName(newEmployee.getFullName());
                        }
                        if (newEmployee.getRole() != null) {
                            employee.setRole(newEmployee.getRole());
                        }

                        return employeeRepository.save(employee);
                    })
                    .orElseThrow(() -> new EmployeeNotExistsException(employeeId));
        }
    }

}

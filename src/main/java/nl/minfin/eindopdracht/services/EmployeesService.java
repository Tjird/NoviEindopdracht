package nl.minfin.eindopdracht.services;

import nl.minfin.eindopdracht.dto.EmployeeDto;
import nl.minfin.eindopdracht.objects.exceptions.CustomerExistsException;
import nl.minfin.eindopdracht.objects.exceptions.IncorrectSyntaxException;
import nl.minfin.eindopdracht.objects.models.Employee;
import nl.minfin.eindopdracht.repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeesService {

    private @Autowired EmployeesRepository employeeRepository;

    public List<Employee> getListOfEmployees() {
        return employeeRepository.findAll();
    }

    public Employee createEmployee(EmployeeDto newEmployee) {
        if (newEmployee.username == null || newEmployee.password == null || newEmployee.fullName ==  null || newEmployee.role == null) throw new IncorrectSyntaxException("NewEmployee");
        if (getEmployeeByName(newEmployee.username) != null) throw new CustomerExistsException("username", newEmployee.username);

        return employeeRepository.save(new Employee(newEmployee.username, newEmployee.password, newEmployee.fullName, newEmployee.role));
    }

    public Employee getEmployeeByName(String name) {
        return employeeRepository.findByUsername(name);
    }

    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

}

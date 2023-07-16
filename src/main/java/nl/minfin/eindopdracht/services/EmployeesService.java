package nl.minfin.eindopdracht.services;

import nl.minfin.eindopdracht.objects.models.Employee;
import nl.minfin.eindopdracht.repositories.EmployeesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeesService {

    private @Autowired EmployeesRepository employeeRepository;

    public List<Employee> getListOfEmployees() {
        return employeeRepository.findAll();
    }

}

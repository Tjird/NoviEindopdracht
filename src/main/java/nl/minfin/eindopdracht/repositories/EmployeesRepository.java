package nl.minfin.eindopdracht.repositories;

import nl.minfin.eindopdracht.objects.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeesRepository extends JpaRepository<Employee, Long> {

    Employee findByUsername(String username);

}

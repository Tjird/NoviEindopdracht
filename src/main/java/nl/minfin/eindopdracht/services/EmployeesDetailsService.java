package nl.minfin.eindopdracht.services;

import nl.minfin.eindopdracht.objects.models.Employee;
import nl.minfin.eindopdracht.repositories.EmployeesRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class EmployeesDetailsService implements UserDetailsService {

    private final EmployeesRepository employeesRepos;

    public EmployeesDetailsService(EmployeesRepository repos) {
        this.employeesRepos = repos;
    }

    public @Override UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> oe = Optional.ofNullable(employeesRepos.findByUsername(username));
        if (oe.isPresent()) {
            Employee employee = oe.get();
            return new EmployeesDetails(employee);
        }
        else {
            throw new UsernameNotFoundException(username);
        }
    }
}

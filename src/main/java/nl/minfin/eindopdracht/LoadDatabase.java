package nl.minfin.eindopdracht;

import nl.minfin.eindopdracht.objects.enums.Role;
import nl.minfin.eindopdracht.objects.models.Employee;
import nl.minfin.eindopdracht.repositories.EmployeesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(EmployeesRepository repository) {
        return args -> {

            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

            List<Employee> employeeList = repository.findAll();

            for (Role role : Role.values()) {
                // Zoek of er een user bestaat met de role uit de for loop (bijv admin)
                Optional<Employee> user = employeeList.stream()
                        .filter(e -> e.getRole().equals(role))
                        .findFirst();

                // Als de user niet bestaat met die role wordt deze aangemaakt
                if (user.isEmpty()) {
                    String username = role.name().toLowerCase();
                    String password = passwordEncoder.encode(username);
                    String fullName = username.substring(0,1).toUpperCase() + username.substring(1) + " " + (username.substring(0,1).toUpperCase() + username.substring(1));
                    log.info("Nieuwe medewerker aan het opslaan in de database, " + repository.save(new Employee(username, password, fullName, role)));
                }
            }
        };
    }

}

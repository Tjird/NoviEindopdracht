package nl.minfin.eindopdracht.objects.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.minfin.eindopdracht.objects.enums.Role;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "employees")
public class Employee {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long employeeId;
    private @Column(nullable = false, unique = true) String username;
    private @Column String password;
    private @Column String fullName;
    private @Column Role role;

    public Employee(String username, String password, String fullName, Role role) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

}

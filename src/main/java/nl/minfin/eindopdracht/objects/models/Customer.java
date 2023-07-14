package nl.minfin.eindopdracht.objects.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "customers")
public class Customer {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long customerId;
    private @JsonIgnore @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL) Set<Repair> repairs = new HashSet<>();
    private @Column String name;
    private @Column String telephoneNumber;
    private @Column String licensePlate;

    public Customer(String name, String telephoneNumber, String licensePlate) {
        this.name = name;
        this.telephoneNumber = telephoneNumber;
        this.licensePlate = licensePlate;
    }
}

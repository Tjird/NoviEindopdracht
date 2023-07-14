package nl.minfin.eindopdracht.services;

import nl.minfin.eindopdracht.objects.models.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmployeesDetails implements UserDetails {

    private final Employee employee;

    public EmployeesDetails(Employee employee) {
        this.employee = employee;
    }

    public @Override Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(employee.getRole().name()));

        return authorities;
    }


    public @Override String getPassword() {
        return employee.getPassword();
    }
    public @Override String getUsername() {
        return employee.getUsername();
    }
    public @Override boolean isAccountNonExpired() {
        return true;
    }
    public @Override boolean isAccountNonLocked() {
        return true;
    }
    public @Override boolean isCredentialsNonExpired() {
        return true;
    }
    public @Override boolean isEnabled() {
        return true;
    }
}

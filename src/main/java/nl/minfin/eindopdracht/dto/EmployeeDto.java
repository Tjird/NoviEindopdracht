package nl.minfin.eindopdracht.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import nl.minfin.eindopdracht.objects.enums.Role;

public class EmployeeDto {

    @Size(min = 4, message = "Gebruikersnaam moet minimaal 4 karakters lang zijn")
    @NotEmpty
    public String username;

    @Size(min = 6, message = "Wachtwoord moet minimaal 6 karakters lang zijn")
    @NotEmpty
    public String password;

    @NotEmpty
    public String fullName;

    @NotEmpty
    public Role role;

}

package nl.minfin.eindopdracht.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AuthDto {

    @Size(min = 4, message = "Gebruikersnaam moet minimaal 4 karakters lang zijn")
    @NotEmpty
    public String username;

    @Size(min = 6, message = "Wachtwoord moet minimaal 6 karakters lang zijn")
    @NotEmpty
    public String password;

}

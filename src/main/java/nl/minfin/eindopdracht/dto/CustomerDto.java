package nl.minfin.eindopdracht.dto;

import jakarta.validation.constraints.NotEmpty;

public class CustomerDto {

    @NotEmpty
    public String customerName;

    @NotEmpty
    public String licensePlate;

    @NotEmpty
    public String telephoneNumber;

}

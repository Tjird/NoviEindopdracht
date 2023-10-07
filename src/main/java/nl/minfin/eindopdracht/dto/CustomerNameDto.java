package nl.minfin.eindopdracht.dto;

import jakarta.validation.constraints.NotEmpty;

public class CustomerNameDto {

    @NotEmpty
    public String fullName;

}

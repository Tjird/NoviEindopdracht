package nl.minfin.eindopdracht.dto;

import jakarta.validation.constraints.NotEmpty;

public class MiscTaskDto {

    @NotEmpty
    public double price;

}

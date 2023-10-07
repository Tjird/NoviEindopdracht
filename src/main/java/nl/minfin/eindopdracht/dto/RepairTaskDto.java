package nl.minfin.eindopdracht.dto;

import jakarta.validation.constraints.NotEmpty;

public class RepairTaskDto {

    @NotEmpty
    public String taskName;

    @NotEmpty
    public double price;

}

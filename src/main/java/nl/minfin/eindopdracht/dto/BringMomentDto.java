package nl.minfin.eindopdracht.dto;

import jakarta.validation.constraints.NotEmpty;

import java.sql.Date;

public class BringMomentDto {

    @NotEmpty
    public Date bringDate;

    @NotEmpty
    public Long customerId;

}

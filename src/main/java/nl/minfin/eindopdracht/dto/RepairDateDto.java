package nl.minfin.eindopdracht.dto;

import jakarta.validation.constraints.NotEmpty;

import java.sql.Date;

public class RepairDateDto {

    @NotEmpty
    public Date repairDate;

}

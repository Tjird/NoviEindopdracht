package nl.minfin.eindopdracht.dto;

import jakarta.validation.constraints.NotEmpty;

public class CustomerIdDto {

    @NotEmpty
    public Long customerId;

}

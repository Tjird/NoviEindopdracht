package nl.minfin.eindopdracht.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class ProblemsFoundDto {

    @NotEmpty
    public List<String> problemsFound;

}

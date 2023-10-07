package nl.minfin.eindopdracht.dto;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class PerformedTasksDto {

    @NotEmpty
    public List<Integer> performedTasks;

}

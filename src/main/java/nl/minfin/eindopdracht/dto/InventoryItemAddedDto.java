package nl.minfin.eindopdracht.dto;

import jakarta.validation.constraints.NotEmpty;
import nl.minfin.eindopdracht.objects.enums.InventoryType;

public class InventoryItemAddedDto {

    @NotEmpty
    public String name;

    @NotEmpty
    public double cost;

    @NotEmpty
    public InventoryType inventoryType;

}

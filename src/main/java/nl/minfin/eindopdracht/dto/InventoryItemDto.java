package nl.minfin.eindopdracht.dto;

import jakarta.persistence.Column;
import nl.minfin.eindopdracht.objects.enums.InventoryType;

public class InventoryItemDto {

    public String name;
    public double cost;
    public int stock;
    public InventoryType inventoryType;

}
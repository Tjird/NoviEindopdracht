package nl.minfin.eindopdracht.objects.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.minfin.eindopdracht.objects.enums.InventoryType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "inventory")
public class InventoryItem implements Comparable<InventoryItem> {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long inventoryItemId;
    private @Column String name;
    private @Column double cost;
    private @Column int stock;
    private @Column InventoryType inventoryType;

    public InventoryItem(String name, double cost, InventoryType inventoryType) {
        this.name = name;
        this.cost = cost;
        this.inventoryType = inventoryType;

        if (inventoryType == InventoryType.CAR_PART) {
            this.stock = 0;
        } else {
            this.stock = -1;
        }
    }

    public InventoryItem(Long Id, String name, double cost, InventoryType inventoryType) {
        this.inventoryItemId = Id;
        this.name = name;
        this.cost = cost;
        this.inventoryType = inventoryType;

        if (inventoryType == InventoryType.CAR_PART) {
            this.stock = 0;
        } else {
            this.stock = -1;
        }
    }

    @Override
    public int compareTo(InventoryItem other) {
        if (this.getInventoryType() == InventoryType.CAR_PART && other.getInventoryType() == InventoryType.MANUAL_ACTION) {
            return -1;
        } else if (this.getInventoryType() == InventoryType.MANUAL_ACTION && other.getInventoryType() == InventoryType.CAR_PART) {
            return 1;
        } else {
            if (this.getName().compareTo(other.getName()) < 0) {
                return -1;
            } else if (this.getName().compareTo(other.getName()) > 0) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}

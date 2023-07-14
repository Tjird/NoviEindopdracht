package nl.minfin.eindopdracht.objects.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.minfin.eindopdracht.objects.enums.CostType;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "costitems")
public class CostItem implements Comparable<CostItem> {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long costItemId;
    private @Column String name;
    private @Column double cost;
    private @Column int stock;
    private @Column CostType costType;

    public CostItem(String name, double cost, CostType costType) {
        this.name = name;
        this.cost = cost;
        this.costType = costType;

        if (costType == CostType.PART) {
            this.stock = 0;
        } else {
            this.stock = -1;
        }
    }

    @Override
    public int compareTo(CostItem other) {
        if (this.getCostType() == CostType.PART && other.getCostType() == CostType.ACTION) {
            return -1;
        } else if (this.getCostType() == CostType.ACTION && other.getCostType() == CostType.PART) {
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

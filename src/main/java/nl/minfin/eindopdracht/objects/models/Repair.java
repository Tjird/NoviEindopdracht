package nl.minfin.eindopdracht.objects.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nl.minfin.eindopdracht.objects.enums.RepairStatus;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "repairs")
public class Repair {

    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long repairId;
    private @ManyToOne
    @JoinColumn(
            name = "customerId",
            referencedColumnName = "customerId"
    ) Customer customer;
    private @OneToOne @JoinColumn(
            name = "fileId",
            referencedColumnName = "fileId"
    ) File file;
    private @Column java.sql.Date bringDate;
    private @Column double price;
    private @Column String problemsFound;
    private @Column java.sql.Date repairDate;
    private @Column Boolean customerAgreed;
    private @Column Date pickupDate;
    private @Column String partsUsed;
    private @Column double otherActionsPrice;
    private @Column RepairStatus completed;
    private @Column Boolean called;
    private @Column Boolean paid;

    public Repair(Customer customer, java.sql.Date bringDate) {
        this.customer = customer;
        this.bringDate = bringDate;
        this.customerAgreed = null;
        this.completed = RepairStatus.UNCOMPLETED;
        this.called = false;
        this.paid = false;
    }

    // partsUsed is stored as a string so it can be stored in one row of a database table.
    public void setPartsUsed(List<Long> partsList) {
        if (partsList != null) {
            String parts = "";

            for (int i = 0; i < partsList.size(); i++) {
                parts += partsList.get(i);

                if (i != partsList.size() - 1) {
                    parts += ",";
                }
            }

            this.partsUsed = parts;
        }
    }

    public List<Long> getPartsUsed() {
        if (partsUsed != null) {
            String[] partsSplit = this.partsUsed.split(",");
            List<Long> partsUsedList = new ArrayList<Long>();

            for (String s : partsSplit) {
                partsUsedList.add(Long.parseLong(s));
            }

            return partsUsedList;
        } else {
            return null;
        }
    }
}
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
    private @Column String tasksPerformed;
    private @Column double miscellaneousTaskPrice;
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

    public void setPerformedTasks(List<Integer> tasksPerformed) {
        if (tasksPerformed != null) {
            String tasks = "";

            for (int i = 0; i < tasksPerformed.size(); i++) {
                tasks += tasksPerformed.get(i);

                if (i != tasksPerformed.size() - 1) {
                    tasks += ", ";
                }
            }

            this.tasksPerformed = tasks;
        }
    }

    public List<Integer> getPerformedTasks() {
        if (tasksPerformed != null) {
            String[] tasksSplit = this.tasksPerformed.split(", ");
            List<Integer> tasksPerformedList = new ArrayList<Integer>();

            for (String s : tasksSplit) {
                tasksPerformedList.add(Integer.parseInt(s));
            }

            return tasksPerformedList;
        } else {
            return null;
        }
    }
}
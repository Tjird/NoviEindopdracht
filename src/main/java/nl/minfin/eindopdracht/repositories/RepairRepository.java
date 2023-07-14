package nl.minfin.eindopdracht.repositories;

import nl.minfin.eindopdracht.objects.models.Repair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {

}

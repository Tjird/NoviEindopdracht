package nl.minfin.eindopdracht.repositories;

import nl.minfin.eindopdracht.objects.models.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface InventoryItemRepository extends JpaRepository<InventoryItem, Integer> {

    InventoryItem findInventoryItemByInventoryItemId(int id);

}

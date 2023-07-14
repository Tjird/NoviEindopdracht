package nl.minfin.eindopdracht.services;

import nl.minfin.eindopdracht.dto.InventoryItemAddedDto;
import nl.minfin.eindopdracht.dto.InventoryItemDto;
import nl.minfin.eindopdracht.objects.enums.InventoryType;
import nl.minfin.eindopdracht.objects.exceptions.InventoryItemExistsException;
import nl.minfin.eindopdracht.objects.exceptions.InventoryItemNotExistsException;
import nl.minfin.eindopdracht.objects.models.InventoryItem;
import nl.minfin.eindopdracht.repositories.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryItemService {

    private @Autowired InventoryItemRepository inventoryItemRepository;

    // Verkrijg alle inventory items uit de database in een lijst
    public List<InventoryItem> getAllInventoryItems() {
        List<InventoryItem> inventoryItems = inventoryItemRepository.findAll();

        Collections.sort(inventoryItems);
        return inventoryItems;
    }

    // Maak een nieuw inventory item aan
    public InventoryItem createInventoryItem(InventoryItemAddedDto inventoryItemAddedDto) {
        List<InventoryItem> inventoryItems = this.getAllInventoryItems();

        // Bekijk of een item met de naam al bestaat in de database
        if (inventoryItems.stream().anyMatch(inventoryItem -> inventoryItemAddedDto.name.equals(inventoryItem.getName()))) {
            throw new InventoryItemExistsException(inventoryItemAddedDto.name);
        }

        InventoryItem inventoryItem = new InventoryItem(inventoryItemAddedDto.name, inventoryItemAddedDto.cost,
                inventoryItemAddedDto.inventoryType);

        return inventoryItemRepository.save(inventoryItem);
    }

    // Pas een inventory item aan
    public InventoryItem editInventoryItem(int inventoryItemId, InventoryItemDto newInventoryItem) {
        Optional<InventoryItem> inventoryItem = inventoryItemRepository.findById(inventoryItemId);

        // Bekijk of het item wel bestaat, zo nee word een expection afgevuurd.
        if (inventoryItem.isEmpty()) throw new InventoryItemNotExistsException(inventoryItemId);

        if (newInventoryItem.inventoryType == InventoryType.MANUAL_ACTION) inventoryItem.get().setStock(-1);
        else inventoryItem.get().setStock(newInventoryItem.stock);

        if (newInventoryItem.cost >= 0) inventoryItem.get().setCost(newInventoryItem.cost);
        if (newInventoryItem.name != null) inventoryItem.get().setName(newInventoryItem.name);

        return inventoryItemRepository.save(inventoryItem.get());
    }

}

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

    public InventoryItem getInventoryItemById(int inventoryItemId) {
        Optional<InventoryItem> inventoryItem = inventoryItemRepository.findById(inventoryItemId);

        if (inventoryItem.isEmpty()) throw new InventoryItemNotExistsException(inventoryItemId);

        return inventoryItem.get();
    }
    public List<InventoryItem> getAllInventoryItems() {
        List<InventoryItem> inventoryItems = inventoryItemRepository.findAll();

        Collections.sort(inventoryItems);
        return inventoryItems;
    }

    public InventoryItem createInventoryItem(InventoryItemAddedDto inventoryItemAddedDto) {
        List<InventoryItem> inventoryItems = this.getAllInventoryItems();

        if (inventoryItems.stream().anyMatch(inventoryItem -> inventoryItemAddedDto.name.equals(inventoryItem.getName()))) {
            throw new InventoryItemExistsException(inventoryItemAddedDto.name);
        }

        InventoryItem inventoryItem = new InventoryItem(inventoryItemAddedDto.name, inventoryItemAddedDto.cost,
                inventoryItemAddedDto.inventoryType);

        return inventoryItemRepository.save(inventoryItem);
    }

    public InventoryItem editInventoryItem(int inventoryItemId, InventoryItemDto newInventoryItem) {
        Optional<InventoryItem> inventoryItem = inventoryItemRepository.findById(inventoryItemId);

        if (inventoryItem.isEmpty()) throw new InventoryItemNotExistsException(inventoryItemId);

        if (newInventoryItem.inventoryType == InventoryType.MANUAL_ACTION) inventoryItem.get().setStock(-1);
        else inventoryItem.get().setStock(newInventoryItem.stock);

        if (newInventoryItem.cost >= 0) inventoryItem.get().setCost(newInventoryItem.cost);
        if (newInventoryItem.name != null) inventoryItem.get().setName(newInventoryItem.name);

        return inventoryItemRepository.save(inventoryItem.get());
    }

}

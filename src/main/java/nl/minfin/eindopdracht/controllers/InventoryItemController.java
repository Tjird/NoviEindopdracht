package nl.minfin.eindopdracht.controllers;

import nl.minfin.eindopdracht.dto.InventoryItemAddedDto;
import nl.minfin.eindopdracht.dto.InventoryItemDto;
import nl.minfin.eindopdracht.objects.models.InventoryItem;
import nl.minfin.eindopdracht.services.InventoryItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/inventory")
public class InventoryItemController {

    private @Autowired InventoryItemService inventoryItemService;

    // Endpoint om alle Inventory items weer te geven in een lijst
    @GetMapping()
    List<InventoryItem> getAllInventoryItems() { return inventoryItemService.getAllInventoryItems();}

    // Endpoint om een inventory item aan te maken met alle waardes, dit kan alleen de role BACKOFFICE
    @PostMapping()
    InventoryItem createInventoryItem(@RequestBody InventoryItemAddedDto inventoryItemAddedDto) {
        return inventoryItemService.createInventoryItem(inventoryItemAddedDto);
    }

    // Endpoint om een inventory item aan te passen, dit kan alleen de role BACKOFFICE
    @PutMapping("/{inventoryItemId}")
    InventoryItem editInventoryItem(@PathVariable Integer inventoryItemId, @RequestBody InventoryItemDto inventoryItemDto) {
        return inventoryItemService.editInventoryItem(inventoryItemId, inventoryItemDto);
    }

}

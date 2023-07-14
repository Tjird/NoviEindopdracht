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

    @GetMapping()
    List<InventoryItem> getAllInventoryItems() { return inventoryItemService.getAllInventoryItems();}

    @PostMapping()
    InventoryItem createInventoryItem(@RequestBody InventoryItemAddedDto inventoryItemAddedDto) {
        return inventoryItemService.createInventoryItem(inventoryItemAddedDto);
    }

    @PutMapping("/{inventoryItemId}")
    InventoryItem editInventoryItem(@PathVariable Integer inventoryItemId, @RequestBody InventoryItemDto inventoryItemDto) {
        return inventoryItemService.editInventoryItem(inventoryItemId, inventoryItemDto);
    }

}

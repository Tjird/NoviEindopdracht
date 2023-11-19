package nl.minfin.eindopdracht.services;

import nl.minfin.eindopdracht.dto.InventoryItemAddedDto;
import nl.minfin.eindopdracht.dto.InventoryItemDto;
import nl.minfin.eindopdracht.objects.enums.InventoryType;
import nl.minfin.eindopdracht.objects.exceptions.InventoryItemExistsException;
import nl.minfin.eindopdracht.objects.exceptions.InventoryItemNotExistsException;
import nl.minfin.eindopdracht.objects.models.Customer;
import nl.minfin.eindopdracht.objects.models.InventoryItem;
import nl.minfin.eindopdracht.repositories.CustomerRepository;
import nl.minfin.eindopdracht.repositories.InventoryItemRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryItemServiceTest {

    @Mock
    InventoryItemRepository inventoryItemRepository;

    @InjectMocks
    InventoryItemService inventoryItemService;

    @Captor
    ArgumentCaptor<InventoryItem> captor;

    List<InventoryItem> inventoryItemList;
    InventoryItem inv1;
    InventoryItem inv2;
    InventoryItem inv3;
    InventoryItem inv4;

    @BeforeEach
    void setUp() {
        inv1 = new InventoryItem(1L, "Banden wissel", 10.00, InventoryType.MANUAL_ACTION);
        inv2 = new InventoryItem(2L, "Veren", 20.00, 3, InventoryType.CAR_PART);
        inv3 = new InventoryItem(3L, "Pitje in voorruit", 49.99, InventoryType.MANUAL_ACTION);
        inv4 = new InventoryItem(4L, "Voorruit", 200.00, 2, InventoryType.CAR_PART);

        inventoryItemList = new ArrayList<>(List.of(inv1, inv2, inv3, inv4));
    }

    @Test
    void getAllInventoryItems() {
        when(inventoryItemRepository.findAll()).thenReturn(inventoryItemList);

        List<InventoryItem> allInventoryItems = inventoryItemService.getAllInventoryItems();

        assertEquals(allInventoryItems, inventoryItemList);
    }

    @Test
    void createInventoryItem() {
        InventoryItemAddedDto inventoryItemToAdd = new InventoryItemAddedDto();

        inventoryItemToAdd.inventoryType = InventoryType.MANUAL_ACTION;
        inventoryItemToAdd.name = "Ruitenwisser vervangen";
        inventoryItemToAdd.cost = 4.99;

        inventoryItemService.createInventoryItem(inventoryItemToAdd);

        verify(inventoryItemRepository, times(1)).save(captor.capture());
        InventoryItem inventoryItem = captor.getValue();

        assertEquals(inventoryItem.getName(), inventoryItemToAdd.name);
        assertEquals(inventoryItem.getCost(), inventoryItemToAdd.cost);
        assertEquals(inventoryItem.getInventoryType(), inventoryItemToAdd.inventoryType);
    }

    @Test
    void createInventoryItemWithExistsName() {
        when(inventoryItemRepository.findAll()).thenReturn(inventoryItemList);
        InventoryItemAddedDto inventoryItemToAdd = new InventoryItemAddedDto();

        inventoryItemToAdd.inventoryType = InventoryType.MANUAL_ACTION;
        inventoryItemToAdd.name = "Banden wissel";
        inventoryItemToAdd.cost = 4.99;

        Assertions.assertThrows(InventoryItemExistsException.class, () -> {
            inventoryItemService.createInventoryItem(inventoryItemToAdd);
        });
    }

    @Test
    void editInventoryItem() {
        when(inventoryItemRepository.findById(3)).thenReturn(Optional.ofNullable(inv3));

        String oldName = inv3.getName();
        double oldCost = inv3.getCost();

        InventoryItemDto inventoryItem = new InventoryItemDto();

        inventoryItem.name = "Pitje in achterruit";
        inventoryItem.inventoryType = InventoryType.MANUAL_ACTION;
        inventoryItem.cost = 39.99;

        inventoryItemService.editInventoryItem(Math.toIntExact(inv3.getInventoryItemId()), inventoryItem);
        verify(inventoryItemRepository, times(1)).save(captor.capture());
        InventoryItem inventoryItemEdited = captor.getValue();

        assertEquals(inventoryItem.name, inventoryItemEdited.getName());
        assertEquals(inventoryItem.cost, inventoryItemEdited.getCost());
        assertEquals(inventoryItem.inventoryType, inventoryItemEdited.getInventoryType());
        assertEquals(inv3.getInventoryItemId(), inventoryItemEdited.getInventoryItemId());

        assertNotEquals(inventoryItemEdited.getName(), oldName);
        assertNotEquals(inventoryItemEdited.getCost(), oldCost);
    }

    @Test
    void editInventoryItemNotExists() {
        InventoryItemDto inventoryItem = new InventoryItemDto();

        inventoryItem.name = "Pitje in achterruit";
        inventoryItem.inventoryType = InventoryType.MANUAL_ACTION;
        inventoryItem.cost = 39.99;

        Assertions.assertThrows(InventoryItemNotExistsException.class, () -> {
            inventoryItemService.editInventoryItem(5, inventoryItem);
        });

    }

    @Test
    void editInventoryItemStock() {
        when(inventoryItemRepository.findById(4)).thenReturn(Optional.ofNullable(inv4));
        InventoryItemDto inventoryItem = new InventoryItemDto();

        inventoryItem.name = "Voorruit";
        inventoryItem.inventoryType = InventoryType.CAR_PART;
        inventoryItem.cost = 301.00;
        inventoryItem.stock = 7;

        inventoryItemService.editInventoryItem(Math.toIntExact(inv4.getInventoryItemId()), inventoryItem);
        verify(inventoryItemRepository, times(1)).save(captor.capture());
        InventoryItem inventoryItemEdited = captor.getValue();

        assertEquals(inventoryItem.name, inventoryItemEdited.getName());
        assertEquals(inventoryItem.cost, inventoryItemEdited.getCost());
        assertEquals(inventoryItem.inventoryType, inventoryItemEdited.getInventoryType());
        assertEquals(inventoryItem.stock, inventoryItemEdited.getStock());
        assertEquals(inv4.getInventoryItemId(), inventoryItemEdited.getInventoryItemId());
    }
}
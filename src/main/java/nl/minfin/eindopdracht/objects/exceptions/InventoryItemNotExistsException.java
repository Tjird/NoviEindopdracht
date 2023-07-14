package nl.minfin.eindopdracht.objects.exceptions;

public class InventoryItemNotExistsException extends RuntimeException {

    public InventoryItemNotExistsException(int id) {
        super("Kan InventoryItem niet vinden met id: " + id);
    }

}

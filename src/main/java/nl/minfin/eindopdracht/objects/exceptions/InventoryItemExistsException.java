package nl.minfin.eindopdracht.objects.exceptions;

public class InventoryItemExistsException extends RuntimeException {

    public InventoryItemExistsException(String name) {
        super("InventoryItem bestaat al met naam: " + name);
    }

}

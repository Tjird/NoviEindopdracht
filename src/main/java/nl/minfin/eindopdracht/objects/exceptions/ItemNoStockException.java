package nl.minfin.eindopdracht.objects.exceptions;

public class ItemNoStockException extends RuntimeException {

    public ItemNoStockException(int id) {
        super("Item (" + id + ") heeft geen voorraad meer");
    }

}

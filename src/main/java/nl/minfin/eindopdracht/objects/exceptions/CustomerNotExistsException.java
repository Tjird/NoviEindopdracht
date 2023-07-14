package nl.minfin.eindopdracht.objects.exceptions;

public class CustomerNotExistsException extends RuntimeException {

    public CustomerNotExistsException(Long id) {
        super("Klant met dit id: " + id.toString() + ", kan niet worden gevonden ");
    }

}

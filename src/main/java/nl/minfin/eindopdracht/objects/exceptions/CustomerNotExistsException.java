package nl.minfin.eindopdracht.objects.exceptions;

public class CustomerNotExistsException extends RuntimeException {

    public CustomerNotExistsException(Long id) {
        super("Could not find customer with id: " + id.toString());
    }

}

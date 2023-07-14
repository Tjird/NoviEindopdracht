package nl.minfin.eindopdracht.objects.exceptions;

public class CustomerExistsException extends RuntimeException {

    public CustomerExistsException(String type, String value) {
        super("Customer do exists with this " + type + ", " + value);
    }

}

package nl.minfin.eindopdracht.objects.exceptions;

public class CustomerExistsException extends RuntimeException {

    public CustomerExistsException(String type, String value) {
        super("Klant bestaat al met dit type " + type + ", en waarde " + value);
    }

}

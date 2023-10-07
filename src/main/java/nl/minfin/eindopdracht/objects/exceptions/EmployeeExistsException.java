package nl.minfin.eindopdracht.objects.exceptions;

public class EmployeeExistsException extends RuntimeException {

    public EmployeeExistsException(String type, String value) {
        super("Gebruiker bestaat al met dit type " + type + ", en waarde " + value);
    }

}

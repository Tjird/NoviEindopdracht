package nl.minfin.eindopdracht.objects.exceptions;

public class ModifyAdminException extends RuntimeException {
    public ModifyAdminException(String action) {
        super("Unable to " + action + " employee with the admin role.");
    }
}

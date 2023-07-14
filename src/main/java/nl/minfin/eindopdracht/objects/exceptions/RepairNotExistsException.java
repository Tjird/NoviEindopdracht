package nl.minfin.eindopdracht.objects.exceptions;

public class RepairNotExistsException extends RuntimeException {
    public RepairNotExistsException(Long id) {
        super("Repair with Id " + id.toString() + " did not exist");
    }
}

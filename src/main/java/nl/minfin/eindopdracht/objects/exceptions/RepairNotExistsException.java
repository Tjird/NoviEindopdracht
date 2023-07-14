package nl.minfin.eindopdracht.objects.exceptions;

public class RepairNotExistsException extends RuntimeException {

    public RepairNotExistsException(Long id) {
        super("Reparatie met het volgende id " + id.toString() + " bestaat nog niet");
    }

}

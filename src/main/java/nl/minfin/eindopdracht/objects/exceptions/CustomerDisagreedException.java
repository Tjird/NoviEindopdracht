package nl.minfin.eindopdracht.objects.exceptions;

public class CustomerDisagreedException extends RuntimeException {
    public CustomerDisagreedException() {
        super("Unable to perform operation. Customer disagreed to the repair.");
    }
}

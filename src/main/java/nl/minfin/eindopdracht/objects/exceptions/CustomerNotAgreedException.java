package nl.minfin.eindopdracht.objects.exceptions;

public class CustomerNotAgreedException extends RuntimeException {

    public CustomerNotAgreedException() {
        super("Unable to perform operation. Customer disagreed to the repair.");
    }

}

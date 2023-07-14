package nl.minfin.eindopdracht.objects.exceptions;

public class CustomerNotAgreedException extends RuntimeException {

    public CustomerNotAgreedException() {
        super("Klant heeft de reparatie geweigerd, daarom kan deze actie niet worden uitgevoerd");
    }

}

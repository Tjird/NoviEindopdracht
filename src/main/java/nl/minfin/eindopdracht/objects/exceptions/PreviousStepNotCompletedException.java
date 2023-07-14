package nl.minfin.eindopdracht.objects.exceptions;

public class PreviousStepNotCompletedException extends RuntimeException {

    public PreviousStepNotCompletedException(String step) {
        super("Vorige stap niet gedaan, stap: " + step + "");
    }

}

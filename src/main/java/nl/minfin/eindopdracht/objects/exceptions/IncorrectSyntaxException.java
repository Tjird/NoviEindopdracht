package nl.minfin.eindopdracht.objects.exceptions;

public class IncorrectSyntaxException extends RuntimeException {

    public IncorrectSyntaxException(String field) {
        super("Geen juiste syntax gebruikt bij de actie: " + field);
    }

}

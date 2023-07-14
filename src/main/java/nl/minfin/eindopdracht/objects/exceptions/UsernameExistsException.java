package nl.minfin.eindopdracht.objects.exceptions;

public class UsernameExistsException extends RuntimeException {
    public UsernameExistsException(String username) {
        super("Username: " + username + " exists.");
    }
}

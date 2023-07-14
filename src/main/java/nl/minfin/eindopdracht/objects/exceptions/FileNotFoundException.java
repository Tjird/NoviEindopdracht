package nl.minfin.eindopdracht.objects.exceptions;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(Long id) {
        super("Bestand niet gevonden met Id: " + id);
    }

}

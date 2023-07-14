package nl.minfin.eindopdracht.objects.exceptions;

public class FileNotFoundException extends RuntimeException {

    public FileNotFoundException(Long id) {
        super("No file uploaded in repair with id: " + id);
    }

}

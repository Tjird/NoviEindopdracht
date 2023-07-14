package nl.minfin.eindopdracht.objects.exceptions;

public class EmployeeNotExistsException extends RuntimeException{

    public EmployeeNotExistsException(Long id) {
        super("Employee with id: " + id.toString() + " doesnt exist.");
    }

}

package nl.minfin.eindopdracht.services;

import nl.minfin.eindopdracht.dto.CustomerDto;
import nl.minfin.eindopdracht.objects.exceptions.CustomerExistsException;
import nl.minfin.eindopdracht.objects.exceptions.IncorrectSyntaxException;
import nl.minfin.eindopdracht.objects.models.Customer;
import nl.minfin.eindopdracht.objects.exceptions.CustomerNotExistsException;
import nl.minfin.eindopdracht.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private @Autowired CustomerRepository customerRepository;

    // Verkrijg een klant op basis van het Id
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotExistsException(customerId));
    }

    // Verkrijg een lijst met klanten op basis van de naam
    public List<Customer> getCustomersByName(String name) {
        return customerRepository.findCustomersByName(name);
    }

    // Creeer een nieuwe klant in het systeem
    public Customer createCustomer(CustomerDto newCustomer) {
        if (newCustomer.customerName == null || newCustomer.telephoneNumber == null || newCustomer.licensePlate ==  null) throw new IncorrectSyntaxException("NewCustomer");

        List<Customer> licensePlateList = customerRepository.findCustomersByLicensePlate(newCustomer.licensePlate);
        List<Customer> telephoneList = customerRepository.findCustomersByTelephoneNumber(newCustomer.telephoneNumber);

        if (licensePlateList.size() > 0) throw new CustomerExistsException("license_plate", licensePlateList.get(0).getLicensePlate());
        else if (telephoneList.size() > 0) throw new CustomerExistsException("telephone_number", telephoneList.get(0).getTelephoneNumber());

        return customerRepository.save(new Customer(newCustomer.customerName, newCustomer.telephoneNumber, newCustomer.licensePlate));
    }

}

package nl.minfin.eindopdracht.services;

import nl.minfin.eindopdracht.dto.CustomerDto;
import nl.minfin.eindopdracht.dto.CustomerIdDto;
import nl.minfin.eindopdracht.objects.exceptions.CustomerExistsException;
import nl.minfin.eindopdracht.objects.exceptions.IncorrectSyntaxException;
import nl.minfin.eindopdracht.objects.models.Customer;
import nl.minfin.eindopdracht.objects.exceptions.CustomerNotExistsException;
import nl.minfin.eindopdracht.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private @Autowired CustomerRepository customerRepository;

    // Verkrijg een klant op basis van het Id
    public Customer getCustomerById(Long customerId) {
        try {
            return customerRepository.findById(customerId).get();
        } catch (NullPointerException e) {
//            throw new CustomerNotExistsException(customerId);
            return null;
        }
        //return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotExistsException(customerId));
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

    public Customer editCustomer(CustomerDto customer, Integer customerId) {
        Optional<Customer> existingCustomer = customerRepository.findById(Long.valueOf(customerId));

        if (existingCustomer.isEmpty()) throw new CustomerNotExistsException(Long.valueOf(customerId));

        if (customer.customerName == null || customer.telephoneNumber == null || customer.licensePlate ==  null) throw new IncorrectSyntaxException("NewCustomer");

        List<Customer> licensePlateList = customerRepository.findCustomersByLicensePlate(customer.licensePlate);
        List<Customer> telephoneList = customerRepository.findCustomersByTelephoneNumber(customer.telephoneNumber);

        if (licensePlateList.size() > 0) throw new CustomerExistsException("license_plate", licensePlateList.get(0).getLicensePlate());
        else if (telephoneList.size() > 0) throw new CustomerExistsException("telephone_number", telephoneList.get(0).getTelephoneNumber());

        Customer editedCustomer = existingCustomer.get();

        editedCustomer.setName(customer.customerName);
        editedCustomer.setLicensePlate(customer.licensePlate);
        editedCustomer.setTelephoneNumber(customer.telephoneNumber);

        return customerRepository.save(editedCustomer);
    }

    public void deleteCustomer(CustomerIdDto customerIdDto) {
        customerRepository.deleteCustomerByCustomerId(customerIdDto.customerId);
    }

}

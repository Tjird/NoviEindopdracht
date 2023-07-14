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

    // Returns all customers.
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotExistsException(customerId));
    }

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

    /*
     * Updates the details of a customer in the database.
     *
     * Will throw exception if the customer id doesnt exist.
     */
    public Customer updateCustomer(Customer newCustomer, Long customerId) {
        return customerRepository.findById(customerId)
                .map(customer -> {
                    if (newCustomer.getName() != null) {
                        customer.setName(newCustomer.getName());
                    }
                    if (newCustomer.getLicensePlate() != null) {
                        customer.setLicensePlate(newCustomer.getLicensePlate());
                    }
                    if (newCustomer.getTelephoneNumber() != null) {
                        customer.setTelephoneNumber(newCustomer.getTelephoneNumber());
                    }

                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new CustomerNotExistsException(customerId));
    }

    /*
     * Deletes a customer from the database.
     *
     * Will throw exception if the customer id doesnt exist.
     */
    public void deleteCustomer(Long customerId) {
        if (customerRepository.findById(customerId).isPresent()) {
            customerRepository.deleteById(customerId);
        } else {
            throw new CustomerNotExistsException(customerId);
        }
    }
}

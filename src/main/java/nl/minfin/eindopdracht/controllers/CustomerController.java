package nl.minfin.eindopdracht.controllers;

import nl.minfin.eindopdracht.dto.CustomerDto;
import nl.minfin.eindopdracht.dto.CustomerIdDto;
import nl.minfin.eindopdracht.dto.CustomerNameDto;
import nl.minfin.eindopdracht.objects.models.Customer;
import nl.minfin.eindopdracht.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Endpoint om klanten te vinden op naam, dit returnt een lijst met klanten
    @GetMapping("/name")
    List<Customer> getCustomersByName(@RequestBody CustomerNameDto name) {

        return customerService.getCustomersByName(name.fullName);
    }

    // Endpoint om een nieuwe klant aan te maken
    @PostMapping()
    Customer createCustomer(@RequestBody CustomerDto newCustomer) {
        return customerService.createCustomer(newCustomer);
    }

    // Endpoint om een gebruiker terug te krijgen op basis van zijn/haar Id
    @GetMapping
    Customer getCustomerById(@RequestBody CustomerIdDto customer) { return customerService.getCustomerById(customer.customerId); }

}

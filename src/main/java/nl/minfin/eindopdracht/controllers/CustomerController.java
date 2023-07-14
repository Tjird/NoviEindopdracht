package nl.minfin.eindopdracht.controllers;

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

    @GetMapping("/name")
    List<Customer> getCustomersByName(@RequestBody CustomerNameDto name) {

        return customerService.getCustomersByName(name.fullName);
    }

    @PostMapping()
    Customer createCustomer(@RequestBody Customer newCustomer) {
        return customerService.createCustomer(newCustomer);
    }

    @GetMapping
    Customer getCustomerById(@RequestBody Customer customer) { return customerService.getCustomerById(customer.getCustomerId()); }

}

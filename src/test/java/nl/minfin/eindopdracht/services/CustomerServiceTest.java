package nl.minfin.eindopdracht.services;

import nl.minfin.eindopdracht.dto.CustomerDto;
import nl.minfin.eindopdracht.dto.CustomerIdDto;
import nl.minfin.eindopdracht.objects.exceptions.CustomerNotExistsException;
import nl.minfin.eindopdracht.objects.models.Customer;
import nl.minfin.eindopdracht.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @Captor
    ArgumentCaptor<Customer> captor;

    List<Customer> customers;
    Customer cus1;
    Customer cus2;
    Customer cus3;
    Customer cus4;

    @BeforeEach
    void setUp() {

        cus1 = new Customer("Tjeerd Rutte", "0624680357", "A-123-BC");
        cus2 = new Customer("Marc Rutte", "06000112345", "A-124-BC");
        cus3 = new Customer("Piet Daal", "0634679432", "A-125-BC");
        cus4 = new Customer();

        cus4.setTelephoneNumber("0687486927");

        customers = new ArrayList<>(List.of(cus1, cus2, cus3, cus4));
    }

    @Test
    void getCustomerById() {
        when(customerRepository.findById(1L)).thenReturn(null);

        assertNull(customerService.getCustomerById(1L));

        customerRepository.save(cus1);
        verify(customerRepository, times(1)).save(captor.capture());

        Customer customer1 = captor.getValue();
        assertEquals(customer1, cus1);
    }

    @Test
    void getCustomersByName() {
        when(customerRepository.findCustomersByName("Rutte")).thenReturn(List.of(cus1, cus2));
        when(customerRepository.findCustomersByName("Daal")).thenReturn(List.of(cus3));

        List<Customer> customerList1 = customerService.getCustomersByName("Rutte");
        List<Customer> customerList2 = customerService.getCustomersByName("Daal");

        assertEquals(customerList1.get(0).getName(), cus1.getName());
        assertEquals(customerList1.get(1).getName(), cus2.getName());
        assertEquals(customerList2.get(0).getName(), cus3.getName());
    }

    @Test
    void createCustomer() {
        CustomerDto customerDto2 = new CustomerDto();
        customerDto2.customerName = cus2.getName();
        customerDto2.telephoneNumber = cus2.getTelephoneNumber();
        customerDto2.licensePlate = cus2.getLicensePlate();

        customerService.createCustomer(customerDto2);

        verify(customerRepository, times(1)).save(captor.capture());
        Customer customer2 = captor.getValue();

        assertEquals(customer2.getName(), cus2.getName());
        assertEquals(customer2.getLicensePlate(), cus2.getLicensePlate());
    }

    @Test
    void editCustomer() {
        cus3.setCustomerId(1L);
        when(customerRepository.findById(1L)).thenReturn(Optional.ofNullable(cus3));
        when(customerRepository.save(any(Customer.class))).thenReturn(cus3);

        Optional<Customer> customer3 = customerRepository.findById(1L);

        customer3.get().setLicensePlate("L-266-DF");

        CustomerDto customerDto3 = new CustomerDto();

        customerDto3.licensePlate = customer3.get().getLicensePlate();
        customerDto3.customerName = customer3.get().getName();
        customerDto3.telephoneNumber = customer3.get().getTelephoneNumber();

        customerService.editCustomer(customerDto3, Math.toIntExact(customer3.get().getCustomerId()));
        verify(customerRepository, times(1)).save(captor.capture());
        Customer customer30 = captor.getValue();

        assertEquals(customer30.getLicensePlate(), cus3.getLicensePlate());
    }

    @Test
    void deleteCustomer() {
        cus3.setCustomerId(1L);
        when(customerRepository.findById(1L)).thenReturn(null);

        CustomerIdDto customerIdDto = new CustomerIdDto();
        customerIdDto.customerId = 1L;

        customerService.deleteCustomer(customerIdDto);
        Customer customer0 = customerService.getCustomerById(1L);

        assertEquals(customer0, null);
    }
}
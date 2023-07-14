package nl.minfin.eindopdracht.repositories;

import nl.minfin.eindopdracht.objects.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findCustomersByName(@Param("name")String name);

}


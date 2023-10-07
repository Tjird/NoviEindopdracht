package nl.minfin.eindopdracht;

import nl.minfin.eindopdracht.controllers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class EindopdrachtApplicationContextLoads {

	private @Autowired EmployeesController employeesController;
	private @Autowired CustomerController customerController;
	private @Autowired InventoryItemController inventoryItemController;
	private @Autowired RepairController repairController;
	private @Autowired AuthController authController;

	@Test
	void contextLoads() {

		assertThat(this.employeesController).isNotNull();
		assertThat(this.customerController).isNotNull();
		assertThat(this.inventoryItemController).isNotNull();
		assertThat(this.repairController).isNotNull();
		assertThat(this.authController).isNotNull();

	}

}

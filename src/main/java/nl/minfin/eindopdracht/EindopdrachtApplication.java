package nl.minfin.eindopdracht;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"nl.*"})
public class EindopdrachtApplication {

	public static void main(String[] args) {
		SpringApplication.run(EindopdrachtApplication.class, args);
	}

}

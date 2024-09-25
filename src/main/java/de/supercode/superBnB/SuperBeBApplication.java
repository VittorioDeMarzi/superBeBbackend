package de.supercode.superBnB;

import de.supercode.superBnB.configurations.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class SuperBeBApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperBeBApplication.class, args);
	}

}

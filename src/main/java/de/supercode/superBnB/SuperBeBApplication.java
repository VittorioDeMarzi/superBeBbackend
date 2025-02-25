package de.supercode.superBnB;

import de.supercode.superBnB.configurations.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableCaching
public class SuperBeBApplication {

	public static void main(String[] args) {
		SpringApplication.run(SuperBeBApplication.class, args);
	}

}

package org.route.details;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "org.route.details")
@EnableAutoConfiguration
public class BusrouteApplication {

	public static void main(String[] args) {
		SpringApplication.run(BusrouteApplication.class, args);
	}

}

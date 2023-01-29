package com.musala.demo.drone;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(
		title = "Drone Service documentation API",
		version = "1.0",
		description = "This page contains the different api-rest services available in this project. " +
				"In the same way, examples and the possibility of making test requests are shown.",
		contact = @Contact(name = "GABRIEL A. PEREZ GUERRA",
				email = "gabrielpga20@gmail.com")
))
@EnableScheduling
public class DroneServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneServiceApplication.class, args);
	}

}

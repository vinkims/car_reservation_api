package com.kigen.car_reservation_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class CarReservationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarReservationApiApplication.class, args);
	}

}

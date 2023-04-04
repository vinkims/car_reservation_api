package com.kigen.car_reservation_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CarReservationApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarReservationApiApplication.class, args);
	}

}

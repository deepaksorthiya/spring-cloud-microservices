package com.example;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.example.model.Car;
import com.example.repo.CarRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class CarServiceApplication implements CommandLineRunner {

	@Autowired
	private CarRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(CarServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Car ID = new Car(1, "ID.", LocalDate.of(2019, Month.DECEMBER, 1));
		Car ID_CROZZ = new Car(2, "ID. CROZZ", LocalDate.of(2021, Month.MAY, 1));
		Car ID_VIZZION = new Car(3, "ID. VIZZION", LocalDate.of(2021, Month.DECEMBER, 1));
		Car ID_BUZZ = new Car(4, "ID. BUZZ", LocalDate.of(2021, Month.DECEMBER, 1));
		Set<Car> vwConcepts = new HashSet<>();
		vwConcepts.add(ID);
		vwConcepts.add(ID_BUZZ);
		vwConcepts.add(ID_CROZZ);
		vwConcepts.add(ID_VIZZION);

		repository.deleteAll();
		repository.saveAll(vwConcepts);
	}
}
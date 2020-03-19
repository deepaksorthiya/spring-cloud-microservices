package com.example;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
		// Electric VWs from https://www.vw.com/electric-concepts/
		// Release dates from
		// https://www.motor1.com/features/346407/volkswagen-id-price-on-sale/
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

@Entity
class Car {
	@Id
	private int id;
	private String name;
	private LocalDate releaseDate;

	public int getId() {
		return id;
	}

	public Car() {
		// JPA
	}

	public Car(int id, String name, LocalDate releaseDate) {
		super();
		this.id = id;
		this.name = name;
		this.releaseDate = releaseDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

}

interface CarRepository extends JpaRepository<Car, Integer> {
}

@RestController
class CarController {

	private CarRepository carRepository;

	public CarController(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	@PostMapping("/cars")
	@ResponseStatus(HttpStatus.CREATED)
	public Car addCar(@RequestBody Car car) {
		return this.carRepository.save(car);
	}

	@GetMapping("/cars/{id}")
	public Car getCar(@PathVariable("id") int id) {
		return this.carRepository.findById(id).get();
	}

	@GetMapping("/cars")
	public List<Car> getCars() {
		return this.carRepository.findAll();
	}

	@DeleteMapping("/cars/{id}")
	public void deleteCar(@PathVariable("id") int id) {
		this.carRepository.deleteById(id);
	}
}
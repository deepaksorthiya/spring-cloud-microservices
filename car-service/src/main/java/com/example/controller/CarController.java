package com.example.controller;

import com.example.model.Car;
import com.example.repo.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    private CarRepository carRepository;

    public CarController(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Car addCar(@RequestBody Car car) {
        return this.carRepository.save(car);
    }

    @GetMapping("/{id}")
    public Car getCar(@PathVariable("id") int id) {
        return this.carRepository.findById(id).get();
    }

    @GetMapping()
    public List<Car> getCars() {
        return this.carRepository.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable("id") int id) {
        this.carRepository.deleteById(id);
    }
}

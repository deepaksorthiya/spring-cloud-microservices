package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.model.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, Integer> {
}

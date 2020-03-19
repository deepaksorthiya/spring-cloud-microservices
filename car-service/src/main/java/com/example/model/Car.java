package com.example.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Car {
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
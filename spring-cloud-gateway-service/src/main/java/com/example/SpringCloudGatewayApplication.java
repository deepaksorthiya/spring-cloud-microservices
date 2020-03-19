package com.example;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringCloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
		// @formatter:off
        		.route("car-service", r -> r.path("/cars/**")
                        .filters(f -> f.hystrix(c -> c.setName("carsFallback")
                        .setFallbackUri("forward:/cars-fallback")))
                        .uri("lb://car-service"))
        		// @formatter:on
				.build();
	}

	@Bean
	@LoadBalanced
	public WebClient.Builder loadBalancedWebClientBuilder() {
		return WebClient.builder();
	}
}

class Car {
	private String name;
	private LocalDate releaseDate;

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

	@Override
	public String toString() {
		return "Car [name=" + name + ", releaseDate=" + releaseDate + "]";
	}

}

@RestController
class FaveCarsController {

	private final WebClient.Builder carClient;

	public FaveCarsController(WebClient.Builder carClient) {
		this.carClient = carClient;
	}

	@GetMapping("/fave-cars")
	public Flux<Car> faveCars() {
		return carClient.build().get().uri("lb://car-service/cars").retrieve().bodyToFlux(Car.class)
				.filter(this::isFavorite);
	}

	private boolean isFavorite(Car car) {
		return car.getName().equals("ID. BUZZ");
	}
}

@RestController
class CarsFallback {

	@GetMapping("/cars-fallback")
	public Flux<Car> noCars() {
		return Flux.empty();
	}
}

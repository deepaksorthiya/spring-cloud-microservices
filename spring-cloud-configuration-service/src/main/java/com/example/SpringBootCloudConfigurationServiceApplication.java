package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SpringBootCloudConfigurationServiceApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpringBootCloudConfigurationServiceApplication.class).run(args);
	}

}
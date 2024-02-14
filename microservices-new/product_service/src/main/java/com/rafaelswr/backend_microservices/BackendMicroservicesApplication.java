package com.rafaelswr.backend_microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class BackendMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendMicroservicesApplication.class, args);
	}

}

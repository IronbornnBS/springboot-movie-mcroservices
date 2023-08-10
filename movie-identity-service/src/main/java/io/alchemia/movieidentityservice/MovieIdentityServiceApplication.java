package io.alchemia.movieidentityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MovieIdentityServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieIdentityServiceApplication.class, args);
	}

}

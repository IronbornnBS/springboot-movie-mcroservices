package io.alchemia.moviegatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MovieGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieGatewayServiceApplication.class, args);
	}

}

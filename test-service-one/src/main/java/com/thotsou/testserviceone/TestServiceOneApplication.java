package com.thotsou.testserviceone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TestServiceOneApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestServiceOneApplication.class, args);
	}

}

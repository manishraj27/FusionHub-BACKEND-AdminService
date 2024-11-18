package com.fusionhub.jfsd.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class FusionHubAdminServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FusionHubAdminServiceApplication.class, args);
		System.out.println("AdminService Project is up and running..!!!");
	}
	
	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
}

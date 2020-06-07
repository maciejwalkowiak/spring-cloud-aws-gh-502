package com.example.gh502test;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

@SpringBootApplication
public class Gh502TestApplication {

	public static void main(String[] args) {
		SpringApplication.run(Gh502TestApplication.class, args);
 	}

 	@Bean
	ApplicationRunner applicationRunner(ResourceLoader resourceLoader) {
		return args -> {
			AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion("eu-central-1").build();
			// maciejwalkowiak-docs is in eu-central-1
			s3.putObject("maciejwalkowiak-docs", "testme.txt", "test me");

			// in application.properties region is set to eu-west-1
			// AWS returns 301 when trying to access this key but no endpoint in execption additional-properties
			Resource resource = resourceLoader.getResource("s3://maciejwalkowiak-docs/testme.txt");
			System.out.println(resource.getFilename());
			System.out.println(resource.getURI());
			System.out.println(resource.contentLength());
		};
	}

}

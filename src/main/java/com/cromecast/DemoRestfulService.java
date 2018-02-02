package com.cromecast;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableAutoConfiguration

public class DemoRestfulService {

	public static void main(String[] args) {
        SpringApplication.run(DemoRestfulService.class, args);
     }	
	
	
}
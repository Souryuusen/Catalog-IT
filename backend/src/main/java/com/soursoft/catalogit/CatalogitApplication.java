package com.soursoft.catalogit;

import com.soursoft.catalogit.utility.ImdbUtility;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CatalogitApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogitApplication.class, args);
	}

}

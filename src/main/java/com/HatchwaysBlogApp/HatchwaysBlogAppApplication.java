package com.HatchwaysBlogApp;

import com.HatchwaysBlogApp.config.SwaggerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(SwaggerConfiguration.class)
@SpringBootApplication
public class HatchwaysBlogAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(HatchwaysBlogAppApplication.class, args);
	}

}

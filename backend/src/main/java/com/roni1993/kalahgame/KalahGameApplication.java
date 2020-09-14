package com.roni1993.kalahgame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KalahGameApplication {

	public static void main(String[] args) {
		SpringApplication.run(KalahGameApplication.class, args);
	}

//	@Bean
//	public OpenAPI kalahOpenApi() {
//		return new OpenAPI().info(new Info()
//				.title("Kalah")
//				.description("Kalah Game")
//				.version("v0.0.1")
//				.license(new License().name("Apache 2.0").url("http://springdoc.org")))
//				.externalDocs(new ExternalDocumentation()
//						.description("Kalah Game")
//						.url("https://github.com/Roni1993"))
//				.components(new Components().addSecuritySchemes("bearer-key",
//						new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
//	}
}

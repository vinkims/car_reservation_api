package com.kigen.car_reservation_api.configs;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
            .group("public-apis")
            .pathsToMatch("/**")
            .pathsToExclude("/actuator/**")
            .build();
    }

    @Bean 
    public OpenAPI carReservationAPI() {
        return new OpenAPI()
            .info(new Info().title("Car Reservation API")
            .description("Car Reservation / Hire API")
            .version("0.0.1")
            .license(new License().name("Apache 2.0").url("htttp://springdoc.org")))
            .externalDocs(new ExternalDocumentation());
    }
}

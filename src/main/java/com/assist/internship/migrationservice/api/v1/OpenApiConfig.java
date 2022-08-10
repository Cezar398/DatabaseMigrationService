package com.assist.internship.migrationservice.api.v1;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI()
    {
        return new OpenAPI()
                .info(new Info()
                        .title("Movie migration service")
                        .description("Service for movie migrations")
                        .contact(new Contact()
                                .name("Catarau Cezar-Iulian")
                                .email("inter.cezar.catarau@assist.ro"))
                );
    }

}

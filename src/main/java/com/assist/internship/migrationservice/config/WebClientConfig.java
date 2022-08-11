package com.assist.internship.migrationservice.config;

import com.assist.internship.migrationservice.config.properties.MigrationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {
    private final MigrationConfig migrationConfig;

    @Bean
    public WebClient webClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .clone()
                .baseUrl(migrationConfig.getBaseUrl())
                .build();
    }
    @Bean
    public WebClient movieIdsWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .clone()
                .baseUrl(migrationConfig.getMovieIdsUrl())
                .filter(ExchangeFilterFunctions.basicAuthentication(migrationConfig.getUsername(), migrationConfig.getPassword()))
                .build();
    }
}

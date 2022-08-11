package com.assist.internship.migrationservice.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "migration")
@Getter
@Setter
public class MigrationConfig {
    private String baseUrl;
    private String token;
    private String username;
    private String password;
    private String movieIdsUrl;
}

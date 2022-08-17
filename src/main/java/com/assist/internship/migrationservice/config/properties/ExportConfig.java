package com.assist.internship.migrationservice.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "export-csv")
@Getter
@Setter
public class ExportConfig {
    private String headerKey;
    private String headerValue;
    private String fileFormat;
    private String contentType;
}

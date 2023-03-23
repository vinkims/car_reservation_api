package com.kigen.car_reservation_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "user")
@Data
public class DefaultValueUserConfig {
    
    private String adminEmail;

    private String adminPassword;

    private String apiClientName;
}

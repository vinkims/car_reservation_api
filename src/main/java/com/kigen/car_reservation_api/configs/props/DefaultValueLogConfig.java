package com.kigen.car_reservation_api.configs.props;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "logging")
@Data
public class DefaultValueLogConfig {
    
    private List<String> allowedMethods;

    private Boolean request;

    private Boolean response;
}

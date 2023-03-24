package com.kigen.car_reservation_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "security")
@Data
public class DefaultValueSecurityConfig {
    
    private String secretKey;

    private Integer tokenValidDuration;

    private Integer tokenValidSecondaryDuration;
}

package com.kigen.car_reservation_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "contact")
public class DefaultValueContactConfig {
    
    private Integer emailTypeId;

    private Integer mobileTypeId;
}

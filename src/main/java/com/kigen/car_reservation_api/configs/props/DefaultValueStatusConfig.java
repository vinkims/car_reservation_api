package com.kigen.car_reservation_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "status")
@Data
public class DefaultValueStatusConfig {
    
    private Integer activeId;

    private Integer completeId;
}

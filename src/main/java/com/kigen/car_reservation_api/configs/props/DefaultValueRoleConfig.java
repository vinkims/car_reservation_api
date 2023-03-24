package com.kigen.car_reservation_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "role")
@Data
public class DefaultValueRoleConfig {
    
    private Integer systemAdminId;
}

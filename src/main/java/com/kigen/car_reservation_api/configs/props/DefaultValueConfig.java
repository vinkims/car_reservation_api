package com.kigen.car_reservation_api.configs.props;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "default.value")
@Data
public class DefaultValueConfig {

    private DefaultValueContactConfig contact;

    private DefaultValueLogConfig logging;

    private DefaultValueRoleConfig role;

    private DefaultValueSecurityConfig security;
    
    private DefaultValueStatusConfig status;

    private DefaultValueUserConfig user;
}

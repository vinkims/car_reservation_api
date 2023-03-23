package com.kigen.car_reservation_api.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
    
    @Value(value = "${default.value.security.secret-key}")
    private String SECRET_KEY;

    @Value(value = "${default.value.security.token-valid-duration}")
    private Integer TOKEN_VALID_PERIOD;

    @Value(value = "${default.value.security.token-valid-secondary-duration}")
    private Integer TOKEN_VALID_SEC_PERIOD;

    @Value(value = "${default.value.user.api-client-name}")
    private String userApiClientName;
}

package com.kigen.car_reservation_api.services.auth;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.kigen.car_reservation_api.dtos.auth.AuthDTO;
import com.kigen.car_reservation_api.dtos.auth.SignoutDTO;
import com.kigen.car_reservation_api.exceptions.InvalidInputException;
import com.kigen.car_reservation_api.models.security.EBlacklistToken;
import com.kigen.car_reservation_api.models.user.EUser;
import com.kigen.car_reservation_api.services.user.IUser;
import com.kigen.car_reservation_api.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SAuth implements IAuth {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private IBlacklist sBlacklist;

    @Autowired
    private IUser sUser;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SUserDetails sUserDetails;
    
    @Override
    public String authenticateUser(AuthDTO authDTO) {

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authDTO.getUserContact(), authDTO.getPassword()));
        } catch (BadCredentialsException ex) {
            log.info("{}", ex.getLocalizedMessage());
            throw new InvalidInputException("invalid credentials provided", "username/password");
        }

        UserDetails userDetails = sUserDetails.loadUserByUsername(authDTO.getUserContact());

        EUser user = sUser.getByIdOrContactValue(authDTO.getUserContact()).get();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());
        claims.put("userRole", user.getRole().getName());

        final String token = jwtUtil.generateToken(userDetails, claims);

        return token;
    }

    @Override
    public EUser getUser(Integer userId) {
        Optional<EUser> user = sUser.getById(userId);
        if (!user.isPresent()) {
            throw new InvalidInputException("user not found", "userId");
        }
        return user.get();
    }

    @Override
    public Boolean signoutUser(SignoutDTO signoutDTO) {
        
        EUser user = getUser(signoutDTO.getUserId());

        EBlacklistToken blacklistToken = sBlacklist.create(signoutDTO.getToken(), user);

        return blacklistToken != null;
    }
    
}

package com.kigen.car_reservation_api.filters;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.kigen.car_reservation_api.services.auth.SUserDetails;
import com.kigen.car_reservation_api.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter {

    @Value(value = "${default.value.logging.allowed-methods}")
    private List<String> allowedMethods;

    @Value(value = "${default.value.logging.request}")
    private Boolean logRequest;

    @Value(value = "${default.value.logging.respose}")
    private Boolean logResponse;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SUserDetails sUserDetails;
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        final String authheader = request.getHeader("Authorization");

        String contactvalue = null;
        String jwt = null;

        if (authheader != null && authheader.startsWith("Bearer ")) {
            jwt = authheader.substring(7);
            contactvalue = jwtUtil.extractUsername(jwt);
        }

        // Removes authentication info for each request
        SecurityContextHolder.getContext().setAuthentication(null);

        if (contactvalue != null) {

            UserDetails userDetails = sUserDetails.loadUserByUsername(contactvalue);

            if (jwtUtil.validateToken(jwt, userDetails)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            logRequestResponse(requestWrapper, responseWrapper);
            responseWrapper.copyBodyToResponse();
        }
    }

    public void logRequestResponse(ContentCachingRequestWrapper requestWrapper, ContentCachingResponseWrapper responseWrapper) 
            throws UnsupportedEncodingException {

        String method = requestWrapper.getMethod();
        if (logRequest && allowedMethods.contains(method)) {
            byte[] requestArray = requestWrapper.getContentAsByteArray();
            String requestStr = new String(requestArray, requestWrapper.getCharacterEncoding());
            log.info("REQUEST:\n[ENDPOINT] - {} {}\n[PAYLOAD] - {}", 
                requestWrapper.getRequestURI(), requestWrapper.getMethod(), requestStr);
        }

        if (logResponse && allowedMethods.contains(method)) {
            byte[] responseArray = responseWrapper.getContentAsByteArray();
            String responseStr = new String(responseArray, responseWrapper.getCharacterEncoding());
            log.info("RESPONSE:\n[PAYLOAD] - {}", responseStr);
        }
    }
    
}

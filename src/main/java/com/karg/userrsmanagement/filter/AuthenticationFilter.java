package com.karg.userrsmanagement.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.karg.userrsmanagement.SpringApplicationContext;
import com.karg.userrsmanagement.config.SecurityConstants;
import com.karg.userrsmanagement.service.UserService;
import com.karg.userrsmanagement.shared.dto.UserDto;
import com.karg.userrsmanagement.ui.model.request.UserLoginRequestModel;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public AuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginRequestModel creds = new ObjectMapper().readValue(request.getInputStream(), UserLoginRequestModel.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.getUsername(),
                            creds.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //this method is called when authentication is successful
        String username = ((User) authResult.getPrincipal()).getUsername();

        // get jwt token
        Algorithm algorithm = Algorithm.HMAC512(SecurityConstants.getTokenSecret());
        var token = JWT.create().
                withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(algorithm);

        UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");

        // get user from UserServiceImpl
        UserDto userDto = userService.getUser(username);//username is email in this case

        //set user id as response header

        response.addHeader("UserID", userDto.getUserId().toString());//userId is public id and id is private db generated id (from entity)

        response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);

    }
}
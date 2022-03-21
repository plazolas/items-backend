package com.oz.demojar.security;

import com.oz.demojar.model.User;
import com.oz.demojar.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials() == null ? null : authentication.getCredentials().toString();

        if(username == null || password == null) {
            return null;
        }

        try {
            User user = userService.getUserByUsername(username);
            if(user.matchPassword(password)) {
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        } catch(Exception e) {
            // user does not exist in db
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}

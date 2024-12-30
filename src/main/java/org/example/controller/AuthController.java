package org.example.controller;

import org.example.service.CustomUserDetailsService;
import org.example.util.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }
}

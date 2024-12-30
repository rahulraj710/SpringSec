package org.example.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    // Accessible only by users with "USER" role
    @PreAuthorize("hasRole('USER')")
    @GetMapping("/test")
    public String testEndpoint() {
        return "Access Granted";
    }

    // Accessible only by users with "ADMIN" role
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Admin Access Granted";
    }
}

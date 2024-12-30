package org.example.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Implement user retrieval logic (from database, etc.)
        if ("test".equals(username)) {
            return User.builder()
                    .username("test")
                    .password("$2a$10$0QOcjAwp28ZzqxyMNV6L0eimRxTHlz7F4g8ewLxO8Ggpo3vvnYNXe") // bcrypt encoded password: "password"
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}

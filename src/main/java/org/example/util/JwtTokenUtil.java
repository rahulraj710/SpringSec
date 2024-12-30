package org.example.util;

import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    private static final String SECRET_KEY = "secret"; // Use a more secure key in production
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;  // 1 hour

    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }

    public Authentication getAuthentication(String token, UserDetailsService userDetailsService) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(extractUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}

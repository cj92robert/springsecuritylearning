package com.gmailatcj92robert.springsecuritylearning.jwt;

import com.gmailatcj92robert.springsecuritylearning.models.Role;
import com.gmailatcj92robert.springsecuritylearning.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtil {
    private String Key = "Klucz";

    public String generateJwtToken(User user) {


        String rolesString = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(","));
        Date now = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + 60 * 60 * 1000);

        return Jwts.builder()
                .setIssuedAt(now)
                .claim("username", user.getUsername())
                .claim("roles", rolesString)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, Key.getBytes())
                .compact();

    }

}

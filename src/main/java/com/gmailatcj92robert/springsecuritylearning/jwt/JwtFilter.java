package com.gmailatcj92robert.springsecuritylearning.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class JwtFilter extends BasicAuthenticationFilter {

    private final String key = "Klucz";


    public JwtFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader("Authorization");

        if (header == null) {
            chain.doFilter(request, response);
        } else {
            header = header.trim();
            if (!StringUtils.startsWithIgnoreCase(header, "Bearer ")) {
                chain.doFilter(request, response);
            } else {
                String jwtToken = header.substring(6);
                if (isValidJwtToken(jwtToken)) {
                    UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationByJWT(jwtToken);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
                chain.doFilter(request, response);
            }
        }
    }

    private UsernamePasswordAuthenticationToken getAuthenticationByJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody();

        String user = claims.get("username").toString();
        Collection<GrantedAuthority> authorities =
                Arrays.stream(claims.get("roles").toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(user, token, authorities);

    }

    private boolean isValidJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(key.getBytes()).parseClaimsJws(token).getBody();
            return true;
        } catch (JwtException e) {
            return false;
        }

    }

}

package com.fusionhub.jfsd.springboot.configuration;


import java.io.IOException;


import javax.crypto.SecretKey;


import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {
        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        if (jwt != null) {
            jwt = jwt.substring(7); // Remove "Bearer " prefix
            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes());
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                String email = String.valueOf(claims.get("email"));
                String role = String.valueOf(claims.get("role"));

                if (!"ADMIN".equals(role)) {
                    throw new BadCredentialsException("Access denied: Role is not ADMIN");
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, AuthorityUtils.createAuthorityList(role));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (ExpiredJwtException | MalformedJwtException | SignatureException e) {
                throw new BadCredentialsException("Invalid JWT token");
            }
        }

        filterChain.doFilter(request, response);
    }
}

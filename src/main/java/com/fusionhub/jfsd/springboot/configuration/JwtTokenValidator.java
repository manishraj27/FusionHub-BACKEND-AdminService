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

        // Bearer jwt_token
        if (jwt != null) {
            jwt = jwt.substring(7); // Remove 'Bearer ' prefix
            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRETE_KEY.getBytes());
                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

                String email = String.valueOf(claims.get("email"));
                String role = String.valueOf(claims.get("role"));

                System.out.println("Extracted role: " + role);  // Log role

                Authentication authentication;
                if ("ADMIN".equalsIgnoreCase(role)) {
                    authentication = new UsernamePasswordAuthenticationToken(email, null,
                            AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else if ("USER".equalsIgnoreCase(role)) {
                    authentication = new UsernamePasswordAuthenticationToken(email, null,
                            AuthorityUtils.createAuthorityList("ROLE_USER"));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new BadCredentialsException("Invalid role in JWT token");
                }

                System.out.println("Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());

            } catch (ExpiredJwtException e) {
                throw new BadCredentialsException("Expired JWT token");
            } catch (MalformedJwtException e) {
                throw new BadCredentialsException("Invalid JWT token");
            } catch (SignatureException e) {
                throw new BadCredentialsException("Invalid JWT signature");
            } catch (Exception e) {
                throw new BadCredentialsException(e.getMessage());
            }
        }

        filterChain.doFilter(request, response);  // Proceed with the filter chain
    }
}

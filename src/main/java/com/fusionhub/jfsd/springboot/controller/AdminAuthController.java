package com.fusionhub.jfsd.springboot.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionhub.jfsd.springboot.configuration.JwtProvider;
import com.fusionhub.jfsd.springboot.model.Admin;
import com.fusionhub.jfsd.springboot.repository.AdminRepository;
import com.fusionhub.jfsd.springboot.request.LoginRequest;
import com.fusionhub.jfsd.springboot.response.AuthResponse;
import com.fusionhub.jfsd.springboot.service.CustomAdminDetailsImpl;
@RestController
@RequestMapping("/admin/auth")
public class AdminAuthController {

    @Autowired
    private CustomAdminDetailsImpl customAdminDetailsImpl;
    
    @Autowired
    private AdminRepository adminRepository;

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody LoginRequest loginRequest) {
   
            String email = loginRequest.getEmail();
            String password = loginRequest.getPassword();

            Admin admin = adminRepository.findByEmail(email);
            if (admin == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Admin not found with email: " + email);
            }

           
            if (!"manish".equals(password) && !"admin".equals(password)) { //admin is password
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid password");
            }

            Authentication authentication = authenticate(email);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
            String jwt = JwtProvider.generateToken(authentication);
            AuthResponse res = new AuthResponse();
            res.setMessage("signin success");
            res.setJwt(jwt);
            
            return new ResponseEntity<>(res, HttpStatus.CREATED);
            
    }
    
    private Authentication authenticate(String username) {
        UserDetails userDetails = customAdminDetailsImpl.loadUserByUsername(username);
        
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }
        
        return new UsernamePasswordAuthenticationToken(
            userDetails, 
            null, 
            userDetails.getAuthorities()
        );
    }
}

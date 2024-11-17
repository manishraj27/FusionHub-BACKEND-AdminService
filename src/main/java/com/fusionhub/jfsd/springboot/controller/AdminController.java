package com.fusionhub.jfsd.springboot.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RestTemplate restTemplate;

    private final String STUDENT_SERVICE_URL = "http://localhost:8081/api/users";  // Replace with the correct URL

    // Get all users
    @GetMapping("/viewall")
    public ResponseEntity<List> getAllUsers(@RequestHeader("Authorization") String jwt) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwt);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<List> response = restTemplate.exchange(
                    STUDENT_SERVICE_URL + "/viewall", HttpMethod.GET, entity, List.class);
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching users: " + e.getMessage());
        }
    }

    // View user profile by ID
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String jwt, @PathVariable("id") Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwt);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<?> response = restTemplate.exchange(
                    STUDENT_SERVICE_URL + "/profile/" + userId, HttpMethod.GET, entity, Object.class);
            return response;
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + e.getMessage());
        }
    }

    // Delete user by ID
    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@RequestHeader("Authorization") String jwt, @PathVariable("id") Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", jwt);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(STUDENT_SERVICE_URL + "/delete/" + userId, HttpMethod.DELETE, entity, Void.class);
            return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user: " + e.getMessage());
        }
    }
}

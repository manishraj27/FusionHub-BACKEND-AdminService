package com.fusionhub.jfsd.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionhub.jfsd.springboot.service.StudentPortfolioManagementService;

@RestController
@RequestMapping("/admin/portfolios")
public class AdminPortfolioController {

    @Autowired
    private StudentPortfolioManagementService portfolioManagementService;

    @GetMapping
    public ResponseEntity<?> getAllPortfolios(@RequestHeader("Authorization") String jwt) {
        try {
            return ResponseEntity.ok(portfolioManagementService.getAllPortfolios(jwt.substring(7)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching portfolios: " + e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getPortfolioByUserId(@RequestHeader("Authorization") String jwt, @PathVariable Long userId) {
        try {
            return ResponseEntity.ok(portfolioManagementService.getPortfolioByUserId(jwt.substring(7), userId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching portfolio: " + e.getMessage());
        }
    }

    @GetMapping("/urls")
    public ResponseEntity<?> getAllPortfolioUrls(@RequestHeader("Authorization") String jwt) {
        try {
            return ResponseEntity.ok(portfolioManagementService.getAllPortfolioUrls(jwt.substring(7)));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching portfolio URLs: " + e.getMessage());
        }
    }
}

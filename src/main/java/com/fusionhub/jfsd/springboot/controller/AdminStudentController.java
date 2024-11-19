package com.fusionhub.jfsd.springboot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fusionhub.jfsd.springboot.service.StudentManagementService;

@RestController
@RequestMapping("/admin/students")
public class AdminStudentController {

    @Autowired
    private StudentManagementService studentManagementService;

    @GetMapping
    public ResponseEntity<?> getAllStudents(@RequestHeader("Authorization") String jwt) {
        try {
            return ResponseEntity.ok(studentManagementService.getAllStudents(jwt.substring(7))); 
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching students");
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<?> getStudentById(@RequestHeader("Authorization") String jwt, @PathVariable Long studentId) {
        try {
            return ResponseEntity.ok(studentManagementService.getStudentById(jwt.substring(7), studentId));
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error fetching student details");
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<?> deleteStudent(@RequestHeader("Authorization") String jwt, @PathVariable Long studentId) {
        try {
            studentManagementService.deleteStudent(jwt.substring(7), studentId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // 204 No Content
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting student: " + e.getMessage());
        }
    }

    
    @PutMapping("/{studentId}/status")  
    public ResponseEntity<?> updateStudentStatus(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long studentId,
            @RequestParam String status) {
        try {
            studentManagementService.updateStudentStatus(jwt.substring(7), studentId, status);
            return ResponseEntity.ok("Student status updated to " + status);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    
}

package com.fusionhub.jfsd.springboot.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fusionhub.jfsd.springboot.service.StudentManagementService;

@RestController
@RequestMapping("/admin/students")
public class AdminStudentController {

    @Autowired
    private StudentManagementService studentManagementService;

    @GetMapping
    public ResponseEntity<?> getAllStudents(@RequestHeader("Authorization") String jwt) {
        try {
            return ResponseEntity.ok(studentManagementService.getAllStudents(jwt.substring(7))); // Remove "Bearer " from the JWT
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
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting student");
        }
    }
}

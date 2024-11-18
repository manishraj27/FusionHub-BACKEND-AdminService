package com.fusionhub.jfsd.springboot.service;

import java.util.List;

public interface StudentManagementService {
   
    List<?> getAllStudents(String jwt) throws Exception;
    
    
    Object getStudentById(String jwt, Long studentId) throws Exception;
    
    
    void deleteStudent(String jwt, Long studentId) throws Exception;
}
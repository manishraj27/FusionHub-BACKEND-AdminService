package com.fusionhub.jfsd.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fusionhub.jfsd.springboot.response.MessageResponse;

@Service
public class StudentManagementServiceImpl implements StudentManagementService {

    @Autowired
    private RestTemplate restTemplate;

    private final String studentServiceUrl = "https://fusionhub-backend-studentservice-production.up.railway.app/adminapi/users"; //eureka server discovery

    @Override
    public List<?> getAllStudents(String jwt) throws Exception {
        String url = studentServiceUrl; 
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        return response.getBody();
    }

    @Override
    public Object getStudentById(String jwt, Long studentId) throws Exception {
        String url = studentServiceUrl + "/" + studentId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        return response.getBody();
    }

    @Override
    public void deleteStudent(String jwt, Long studentId) throws Exception {
        String url = studentServiceUrl + "/" + studentId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Call to StudentService for deleting the student
        ResponseEntity<MessageResponse> response = restTemplate.exchange(
                url, HttpMethod.DELETE, entity, MessageResponse.class);
        
        // Check if the deletion was successful
        if (!response.getStatusCode().is2xxSuccessful()) {
            throw new Exception("Failed to delete student. Server returned: " + response.getStatusCode());
        }
    }

    
    @Override
    public void updateStudentStatus(String jwt, Long studentId, String status) throws Exception {
 
        String upperStatus = status.toUpperCase();
        if (!upperStatus.equals("ACCEPTED") && !upperStatus.equals("REJECTED") && !upperStatus.equals("PENDING")) {
            throw new IllegalArgumentException("Invalid status. Use 'ACCEPTED', 'REJECTED', or 'PENDING'.");
        }

        String url = studentServiceUrl + "/" + studentId + "/status?status=" + upperStatus;
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                url,
                HttpMethod.PUT,  
                entity,
                String.class
            );
            
            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new Exception("Failed to update status. Server returned: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new Exception("Error updating student status: " + e.getMessage());
        }
    }
    
}

package com.fusionhub.jfsd.springboot.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StudentManagementServiceImpl implements StudentManagementService {

    @Autowired
    private RestTemplate restTemplate;

    private final String studentServiceUrl = "http://localhost:2004/adminapi/users"; // Adjust the base URL for StudentService

    @Override
    public List<?> getAllStudents(String jwt) throws Exception {
        String url = studentServiceUrl; // Add any specific endpoint path if needed
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

        restTemplate.exchange(url, HttpMethod.DELETE, entity, Void.class);
    }
}

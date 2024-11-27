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
public class StudentPortfolioManagementServiceImpl implements StudentPortfolioManagementService {

    @Autowired
    private RestTemplate restTemplate;

    private final String portfolioServiceUrl = "http://FusionHub-StudentService/adminapi/users";

    @Override
    public List<?> getAllPortfolios(String jwt) throws Exception {
        String url = portfolioServiceUrl + "/allportfolios";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        return response.getBody();
    }

    @Override
    public Object getPortfolioByUserId(String jwt, Long userId) throws Exception {
        String url = portfolioServiceUrl + "/portfolio/" + userId;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Object> response = restTemplate.exchange(url, HttpMethod.GET, entity, Object.class);
        return response.getBody();
    }

    @Override
    public List<?> getAllPortfolioUrls(String jwt) throws Exception {
        String url = portfolioServiceUrl + "/portfolio/urls";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwt);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.GET, entity, List.class);
        return response.getBody();
    }
}

package com.fusionhub.jfsd.springboot.service;


import java.util.List;

public interface StudentPortfolioManagementService {
    List<?> getAllPortfolios(String jwt) throws Exception;
    Object getPortfolioByUserId(String jwt, Long userId) throws Exception;
    List<?> getAllPortfolioUrls(String jwt) throws Exception;
}

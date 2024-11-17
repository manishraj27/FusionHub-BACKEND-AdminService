package com.fusionhub.jfsd.springboot.configuration;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/admin/**") 
                .allowedOrigins("http://localhost:5173")  //admin service
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders("Authorization") 
                .maxAge(3600L); // Cache duration for preflight responses
    }
}

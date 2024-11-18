package com.fusionhub.jfsd.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fusionhub.jfsd.springboot.model.Admin;
import com.fusionhub.jfsd.springboot.service.AdminService;

@RestController
@RequestMapping("/admin/")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
    @GetMapping("/profile")
    public ResponseEntity<Admin> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception{
    	
    	Admin user = adminService.findAdminProfileByJwt(jwt);
    	return new ResponseEntity<>(user, HttpStatus.OK);
    }
}

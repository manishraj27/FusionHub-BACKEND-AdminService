package com.fusionhub.jfsd.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fusionhub.jfsd.springboot.configuration.JwtProvider;
import com.fusionhub.jfsd.springboot.model.Admin;
import com.fusionhub.jfsd.springboot.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public Admin findAdminProfileByJwt(String jwt) throws Exception {
		String email = JwtProvider.getEmailFromToken(jwt);
		String role = JwtProvider.getRoleFromToken(jwt);
		return findAdminByEmail(email);
	}

	@Override
	public Admin findAdminByEmail(String email) throws Exception {
		Admin admin = adminRepository.findByEmail(email);
		if(admin == null) {
			throw new Exception("ADMIN NOT FOUND");
		}
		return admin;
	}


}

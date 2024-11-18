package com.fusionhub.jfsd.springboot.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fusionhub.jfsd.springboot.model.Admin;
import com.fusionhub.jfsd.springboot.repository.AdminRepository;

@Service
public class CustomAdminDetailsImpl implements UserDetailsService{

	@Autowired
	private AdminRepository adminRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin user = adminRepository.findByEmail(username);
		
		if(user == null) {
			throw new UsernameNotFoundException("Admin not found with email "+username);
		}
		
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ADMIN"));
		
		return new org.springframework.security.core.userdetails.User(user.getEmail() ,user.getPassword(), 
				authorities);
	}

	
}

package com.fusionhub.jfsd.springboot.service;

import com.fusionhub.jfsd.springboot.model.Admin;

public interface AdminService {
	Admin findAdminProfileByJwt(String jwt) throws Exception;
	
	Admin findAdminByEmail(String email) throws Exception;
}

package com.fusionhub.jfsd.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fusionhub.jfsd.springboot.model.Admin;
import java.util.List;


public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findByEmail(String email);
}

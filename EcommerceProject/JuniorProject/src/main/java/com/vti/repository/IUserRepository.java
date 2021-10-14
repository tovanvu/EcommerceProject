package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.vti.model.entity.Users;

public interface IUserRepository extends JpaRepository<Users, Long>, JpaSpecificationExecutor<Users> {

	public Users findByEmail(String name);
	
	public boolean existsByEmail(String email);
}

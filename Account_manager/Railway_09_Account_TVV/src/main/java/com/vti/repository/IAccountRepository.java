package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vti.entity.Account;


public interface IAccountRepository extends JpaRepository<Account, Short>, JpaSpecificationExecutor<Account> {
	
	public Account findByUsername(String username);
	
	public Account findByEmail(String email); 
	
	public boolean existsByUsername(String name);
	
	public boolean existsByEmail(String email);

}
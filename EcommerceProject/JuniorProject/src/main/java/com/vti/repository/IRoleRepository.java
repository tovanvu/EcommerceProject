package com.vti.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import com.vti.model.entity.Role;

/**
 * 
 * Represents a IRoleRepository
 *
 *
 * @author PTrXuan Created on Sep 16, 2021
 */
public interface IRoleRepository extends JpaRepository<Role, Long>, JpaSpecificationExecutor<Role> {

	public Role findByName(String name);

	public Role findByRoleCode(String roleCode);

	public boolean existsByName(String name);

	public boolean existsByRoleCode(String roleCode);

}

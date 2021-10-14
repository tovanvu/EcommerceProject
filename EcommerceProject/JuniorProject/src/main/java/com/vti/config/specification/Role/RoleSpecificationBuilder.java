package com.vti.config.specification.Role;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import com.vti.model.entity.Role;

public class RoleSpecificationBuilder {

	public static Specification<Role> buildWhere(Long id, String roleCode, String roleName, LocalDate createdFrom,
			LocalDate createdTo, LocalDate updatedFrom, LocalDate updatedTo, String emailUserCreate,
			String emailUserUpdate) {

		Specification<Role> searchQuery = null;

		if (!ObjectUtils.isEmpty(roleCode)) {

			RoleSpecification roleCodeSpec = new RoleSpecification("roleCode", "LIKE", roleCode);
			searchQuery = Specification.where(roleCodeSpec);
		}

		if (!ObjectUtils.isEmpty(roleName)) {

			RoleSpecification roleNameSpec = new RoleSpecification("name", "LIKE", roleName);
			if (searchQuery == null) {
				searchQuery = Specification.where(roleNameSpec);
			} else {
				searchQuery = searchQuery.and(roleNameSpec);
			}

		}
		
		if (!ObjectUtils.isEmpty(emailUserCreate)) {
			RoleSpecification emailUserCreateSpec = new RoleSpecification("createdBy.email", "LIKE", emailUserCreate);
			if (searchQuery == null) {
				searchQuery = Specification.where(emailUserCreateSpec);
			} else {
				searchQuery = searchQuery.and(emailUserCreateSpec);
			}
		}
		
		if (!ObjectUtils.isEmpty(emailUserUpdate)) {
			RoleSpecification emailUserUpdateSpec = new RoleSpecification("updatedBy.email", "LIKE", emailUserUpdate);
			if (searchQuery == null) {
				searchQuery = Specification.where(emailUserUpdateSpec);
			} else {
				searchQuery = searchQuery.and(emailUserUpdateSpec);
			}
		}
		
		if (!ObjectUtils.isEmpty(id)) {
			Specification<Role> idSpec = new RoleSpecification("id", "=", id);
			if (searchQuery == null) {
				searchQuery = Specification.where(idSpec);
			} else {
				searchQuery = searchQuery.and(idSpec);
			}
		}
		
		if (createdFrom != null && createdTo != null) {
			RoleSpecification createdFromSpec = new RoleSpecification("createdAt", ">=", createdFrom);
			if (searchQuery == null) {
				searchQuery = Specification.where(createdFromSpec);
			} else {
				searchQuery = searchQuery.and(createdFromSpec);
			}
		}

		if (createdFrom != null && createdTo != null) {
			RoleSpecification createdToSpec = new RoleSpecification("createdAt", "<=", createdTo);
			if (searchQuery == null) {
				searchQuery = Specification.where(createdToSpec);
			} else {
				searchQuery = searchQuery.and(createdToSpec);
			}
		}
		
		if (updatedFrom != null && updatedTo != null) {
			RoleSpecification updatedFromSpec = new RoleSpecification("updatedAt", ">=", updatedFrom);
			if (searchQuery == null) {
				searchQuery = Specification.where(updatedFromSpec);
			} else {
				searchQuery = searchQuery.and(updatedFromSpec);
			}
		}

		if (updatedFrom != null && updatedTo != null) {
			RoleSpecification updatedToSpec = new RoleSpecification("updatedAt", "<=", updatedTo);
			if (searchQuery == null) {
				searchQuery = Specification.where(updatedToSpec);
			} else {
				searchQuery = searchQuery.and(updatedToSpec);
			}
		}

		return searchQuery;

	}
}

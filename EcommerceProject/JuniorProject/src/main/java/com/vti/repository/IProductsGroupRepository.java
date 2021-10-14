package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vti.model.entity.ProductGroups;

public interface IProductsGroupRepository extends JpaRepository<ProductGroups, Long> {
	public boolean existsById(Long id);
	public boolean existsByName(String name);
}

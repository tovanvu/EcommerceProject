package com.vti.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vti.model.entity.GroupVariants;
import com.vti.model.entity.ProductGroups;

public interface IGroupVariantRepository extends JpaRepository<GroupVariants, Long> {
	public List<GroupVariants> findByGroup(ProductGroups group);
	
	public boolean existsByVariantName(String name);
}

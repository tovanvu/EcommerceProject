package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vti.model.entity.Products;

public interface IProductsRepository extends JpaRepository<Products, Long>, JpaSpecificationExecutor<Products> {

}

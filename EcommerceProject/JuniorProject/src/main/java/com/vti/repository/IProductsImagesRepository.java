package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vti.model.entity.ProductImages;

public interface IProductsImagesRepository extends JpaRepository<ProductImages, Long> {

}

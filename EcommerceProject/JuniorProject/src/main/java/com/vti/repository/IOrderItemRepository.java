package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vti.model.entity.OrderItems;

public interface IOrderItemRepository extends JpaRepository<OrderItems, Long> {

}

package com.vti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.vti.model.entity.Orders;

public interface IOrderRepository extends JpaRepository<Orders, Long>, JpaSpecificationExecutor<Orders> {

}

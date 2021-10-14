package com.vti.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vti.model.entity.Cart;

@Repository
public interface IRedisCartRepository extends CrudRepository<Cart, String> {

}

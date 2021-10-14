package com.vti.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.vti.model.entity.AccessToken;

@Repository
public interface IRedisTokenRepository extends CrudRepository<AccessToken, String> {

}

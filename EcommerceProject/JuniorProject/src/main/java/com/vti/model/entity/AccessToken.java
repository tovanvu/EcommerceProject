package com.vti.model.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.redis.core.RedisHash;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@RedisHash("Token")
public class AccessToken implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private List<TokenUser> tokens;
}

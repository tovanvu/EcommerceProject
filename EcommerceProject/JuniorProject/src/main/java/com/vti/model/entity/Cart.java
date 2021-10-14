package com.vti.model.entity;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.redis.core.RedisHash;

import com.vti.model.dto.ItemInCart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
@RedisHash("Cart")
public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private List<ItemInCart> item;
}

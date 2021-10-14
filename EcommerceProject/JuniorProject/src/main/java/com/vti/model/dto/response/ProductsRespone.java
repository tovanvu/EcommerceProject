package com.vti.model.dto.response;

import com.vti.model.dto.ProductsGroupsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ProductsRespone {
	private Long id;
	private String name;
	private int price;
	private String description;
	private ProductsGroupsDTO group;

}

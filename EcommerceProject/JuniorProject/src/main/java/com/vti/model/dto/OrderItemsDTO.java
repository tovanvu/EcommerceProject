package com.vti.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class OrderItemsDTO {
	private ProductsDTO product;
	private int quantity;
	private GroupsVariantsDTO variant;
}

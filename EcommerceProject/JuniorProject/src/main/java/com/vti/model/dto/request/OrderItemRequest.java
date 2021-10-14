package com.vti.model.dto.request;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import com.vti.validation.productValidation.ProductExists;
import com.vti.validation.variantValidation.VariantNotExists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemRequest {

	@Valid
	private ProductRequest product;

	@Min(value = 1)
	private int quantity;

	@Valid
	private GroupVariantRequest variant;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class ProductRequest {
		@ProductExists
		private Long id;
	}

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public class GroupVariantRequest {
		@VariantNotExists
		private Long id;
	}
}

package com.vti.model.dto;

import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vti.validation.productValidation.ProductExists;
import com.vti.validation.variantValidation.VariantNotExists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemInCart {
	@ProductExists
	@JsonProperty("product_id")
	private long productID;

	@VariantNotExists
	@JsonProperty("variant_id")
	private long variantID;

	@Positive
	private int quantity;
}

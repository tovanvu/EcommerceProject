package com.vti.model.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class ProductImagesRespone {
	private Long id;

	@JsonProperty("product_id")
	private Long productId;

	private String path;
}

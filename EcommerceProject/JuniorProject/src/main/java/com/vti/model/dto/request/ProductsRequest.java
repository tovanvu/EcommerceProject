package com.vti.model.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductsRequest {
	@Length(max = 255)
	@NotBlank
	private String name;

	@Min(value = 0)
	private int price;

	@Length(max = 255)
	private String description;

	@JsonProperty("group_id")
	private Long groupId;

}

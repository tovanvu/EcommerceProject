package com.vti.model.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
	@Length(max = 255)
	@NotBlank
	private String name;

	@Length(max = 255)
	@NotBlank
	private String address;

	@Length(max = 255)
	@NotBlank
	private String city;

	@Length(max = 10)
	@NotBlank
	private String zip;

	@Length(max = 255)
	private String comment;

	@Length(max = 255)
	@NotBlank
	private String type;

	@Valid
	@NotEmpty
	private List<OrderItemRequest> items;
}

package com.vti.model.dto.request;

import java.util.List;

import javax.validation.Valid;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateRequest {
	@Length(max = 255)
	@Nullable
	private String name;

	@Length(max = 255)
	@Nullable
	private String address;

	@Length(max = 255)
	@Nullable
	private String city;

	@Length(max = 10)
	@Nullable
	private String zip;

	@Nullable
	@Length(max = 255)
	private String comment;

	@Length(max = 255)
	@Nullable
	private String type;

	@Valid
	@Nullable
	private List<OrderItemRequest> items;
}
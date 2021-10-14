package com.vti.model.dto.request;

import javax.validation.constraints.Min;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductUpdateRequest {
	@Nullable
	private String name;

	@Nullable
	@Min(value = 0)
	private int price;

	@Nullable
	private String description;

	@Nullable
	@JsonProperty("group_id")
	private Long groupId;

}

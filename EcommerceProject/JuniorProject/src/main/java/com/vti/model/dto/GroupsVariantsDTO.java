package com.vti.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GroupsVariantsDTO {
	private Long id;
	
	@JsonProperty("variant_name")
	private String variantName;
}

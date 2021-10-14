package com.vti.model.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vti.model.dto.GroupsVariantsDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductGroupRespone {
	private Long id;

	@JsonProperty("group_name")
	private String groupName;

	@JsonProperty("variants")
	private List<GroupsVariantsDTO> groupsVariants;
}

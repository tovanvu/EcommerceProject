package com.vti.model.dto.request;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.vti.validation.group.GroupExists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductGroupsRequest {
	@GroupExists
	@Length(max = 255)
	@NotBlank
	private String name;

	@Valid
	@NotEmpty
	private List<GroupsVariantsRequest> variants;

}

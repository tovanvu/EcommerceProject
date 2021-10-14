package com.vti.model.dto.request;

import java.util.List;
import javax.validation.Valid;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import com.vti.validation.group.GroupExists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GroupUpdateRequest {
	@Length(max = 255)
	@Nullable
	@GroupExists
	private String name;

	@Valid
	@Nullable
	private List<GroupsVariantsRequest> variants;

}

package com.vti.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RankDTO {
	private Long id;

	@JsonProperty("rank_name")
	private String rankName;
}

/**
 * 
 */
package com.vti.model.dto.response;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is RankResponse
 * 
 * @Description: list rank
 * @author: KienTT
 * @create_date: Sep 14, 2021
 * @version: 1.0
 * @modifer: KienTT
 * @modifer_date: Sep 14, 2021
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankResponse {
	private Long id;
	@JsonProperty("name")
	private String name;
 
	@JsonProperty("created_at")
	private LocalDate created_at;

	@JsonProperty("updated_at")
	private LocalDate updated_at;

	@JsonProperty("created_by")
	private Users created_by;

	@JsonProperty("updated_by")
	private Users updated_by;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	private class Users {
		private Long id;

		private String name;
	}

	public RankResponse(Long id, String name, LocalDate created_at, LocalDate updated_at, Long createdByID,
			String createdByName, Long updatedByID, String updatedByName) {
		super();
		this.id = id;
		this.name = name;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.created_by = new Users(createdByID, createdByName);
		this.updated_by = new Users(updatedByID, updatedByName);
	}


}

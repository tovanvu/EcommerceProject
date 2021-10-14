/**
 * 
 */
package com.vti.model.dto.response;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is RankResponseDetail
 * 
 * @Description: ...
 * @author: KienTT
 * @create_date: Sep 15, 2021
 * @version: 1.0
 * @modifer: KienTT
 * @modifer_date: Sep 15, 2021
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RankResponseDetail {
	private Long id;

	private String name;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate created_at;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate updated_at;

	private UserResponse created_by;

	private UserResponse updated_by;
}

/**
 * 
 */
package com.vti.model.dto.request;

import javax.validation.constraints.NotBlank;

import com.vti.validation.rank.IRankCheckExist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is RankRequest
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
public class RankRequest {
	@IRankCheckExist
	@NotBlank
	private String name;
}

package com.vti.model.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class is RankResponsePageable
 * 
 * @Description: rank response pageable to search rank
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
public class RankResponsePageable {
	private List<RankResponse> list;

	private int total;

	private int page;
}

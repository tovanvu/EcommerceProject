package com.vti.model.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchGroupResponse {
	private List<ProductGroupRespone> list;
	private int total;
	private int page;
}

package com.vti.model.dto;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductsGroupsDTO {
	private Long id;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate created;
	private String groupName;
	private Integer price;

}

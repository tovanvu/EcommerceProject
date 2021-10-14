package com.vti.model.dto.response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vti.model.dto.OrderItemsDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrdersRespone {

	private Long id;
	
	private String name;

	private String address;

	private String city;
	
	private String zip;
	
	private String status;

	private String comment;

	@JsonProperty("total_price")
	private long totalPrice;

	private String type;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate created;

	private List<OrderItemsDTO> items;

}

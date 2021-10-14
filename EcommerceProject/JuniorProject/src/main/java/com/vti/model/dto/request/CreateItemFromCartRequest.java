package com.vti.model.dto.request;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateItemFromCartRequest {
	@Length(max = 255)
	private String name;
	
	@Length(max = 255)
	private String address;
	
	@Length(max = 255)
	private String city;
	
	@Length(max = 10)
	private String zip;
	
	@Length(max = 255)
	private String comment;
	
	@Length(max = 255)
	private String type;
}

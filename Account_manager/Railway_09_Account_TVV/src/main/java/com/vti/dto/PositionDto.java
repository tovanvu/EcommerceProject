package com.vti.dto;

public class PositionDto {
	private short id;
	private String name;

	public short getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public PositionDto(short id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}

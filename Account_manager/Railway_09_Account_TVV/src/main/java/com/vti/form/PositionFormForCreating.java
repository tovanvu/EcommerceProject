package com.vti.form;
public class PositionFormForCreating {
	private com.vti.entity.Position.PositionName name;

	public enum PositionName {
		Dev, Test, Scrum_Master, PM
	}

	public PositionFormForCreating() {
		super();
	}

	public com.vti.entity.Position.PositionName getName() {
		return name;
	}

	public void setName(com.vti.entity.Position.PositionName name) {
		this.name = name;
	}

}

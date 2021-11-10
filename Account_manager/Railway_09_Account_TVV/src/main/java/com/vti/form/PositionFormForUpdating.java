package com.vti.form;

public class PositionFormForUpdating {
	private com.vti.entity.Position.PositionName name;

	public enum PositionName {
		Dev, Test, Scrum_Master, PM
	}

	public PositionFormForUpdating() {
		super();
	}

	public com.vti.entity.Position.PositionName getName() {
		return name;
	}

	public void setName(com.vti.entity.Position.PositionName name) {
		this.name = name;
	}

}

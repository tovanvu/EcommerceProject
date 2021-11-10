package com.vti.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class OnSendRegistrationUserConfirmViaEmailEvent extends ApplicationEvent {

	private static final long serialVersionUID = 1L;

	private String email;

	public OnSendRegistrationUserConfirmViaEmailEvent(String email) {
		super(email);
		this.email = email;
	}

}

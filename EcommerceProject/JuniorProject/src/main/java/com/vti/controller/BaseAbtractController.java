package com.vti.controller;

import java.security.Principal;
import org.springframework.security.core.Authentication;
import com.vti.model.login.LoginUserDetails;

public class BaseAbtractController {

	protected long getCustomerId(Principal principal) {
		Authentication auth = (Authentication) principal;
		LoginUserDetails userDetails = (LoginUserDetails) auth.getPrincipal();
		return userDetails.getLoginUser().getId();

	}

	protected String getCustomerRole(Principal principal) {
		Authentication auth = (Authentication) principal;
		LoginUserDetails userDetails = (LoginUserDetails) auth.getPrincipal();
		return userDetails.getLoginUser().getRole().getName();

	}
}

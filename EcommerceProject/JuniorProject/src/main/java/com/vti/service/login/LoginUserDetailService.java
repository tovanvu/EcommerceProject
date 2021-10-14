package com.vti.service.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.vti.model.entity.Users;
import com.vti.model.login.LoginUserDetails;
import com.vti.repository.IUserRepository;

@Component
public class LoginUserDetailService implements UserDetailsService {

	@Autowired
	private IUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		Users u = userRepository.findByEmail(s);
		if (u == null) {
			throw new UsernameNotFoundException(s + " is not found.");
		}
		return new LoginUserDetails(u);
	}
}
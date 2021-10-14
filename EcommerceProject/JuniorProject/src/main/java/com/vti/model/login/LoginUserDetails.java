package com.vti.model.login;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.vti.model.entity.Users;
import lombok.Data;

@SuppressWarnings("serial")
@Data
public class LoginUserDetails implements UserDetails {
	
	private final Users loginUser;

	public LoginUserDetails(Users loginUser) {
		this.loginUser = loginUser;
	}

	public Users getLoginUser() {
		return loginUser;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String userRole = loginUser.getRole().getRoleCode();
		List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + userRole));
		return authorities;
	}

	@Override
	public String getPassword() {
		return loginUser.getPassWord();
	}

	@Override
	public String getUsername() {
		return loginUser.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
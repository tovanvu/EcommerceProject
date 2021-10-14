package com.vti.service.login;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.vti.model.dto.RankDTO;
import com.vti.model.dto.RoleDTO;
import com.vti.model.dto.response.UserResponse;
import com.vti.model.entity.AccessToken;
import com.vti.model.entity.TokenUser;
import com.vti.model.entity.Users;
import com.vti.model.login.LoginUserDetails;
import com.vti.model.login.request.LoginRequest;
import com.vti.model.login.response.LoginResponse;
import com.vti.repository.IRedisTokenRepository;
import com.vti.ultils.TokenHelper;

@Service
public class LoginService {
	@Autowired
	private IRedisTokenRepository tokenRepository;

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private TokenHelper tokenHelper;

	@Value("${jwt.expires_in}")
	private int EXPIRES_IN;

	public LoginResponse login(LoginRequest form) {
		Authentication authentication = null;

		// Perform verification with userId and password
		Authentication request = new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPasswd());

		authentication = authManager.authenticate(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Store authenticated user information
		LoginUserDetails principal = (LoginUserDetails) authentication.getPrincipal();
		Users user = principal.getLoginUser();
		String jwt = tokenHelper.generateToken(user.getEmail());

		RankDTO rankDTO = new RankDTO(user.getRank() == null ? null : user.getRank().getId(),
				user.getRank() == null ? null : user.getRank().getRankName());
		RoleDTO roleDTO = new RoleDTO(user.getRole().getId(), user.getRole().getRoleCode(), user.getRole().getName());
		UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getEmail(), roleDTO, rankDTO);

		AccessToken accessToken = tokenRepository.findById(user.getEmail()).orElse(null);

		List<TokenUser> tokens = new ArrayList<>();

		if (accessToken == null) {
			AccessToken accessToken2 = new AccessToken();
			accessToken2.setId(user.getEmail());
			TokenUser tokenUser = new TokenUser(jwt);
			tokens.add(tokenUser);
			accessToken2.setTokens(tokens);
			tokenRepository.save(accessToken2);
		} else {

			List<TokenUser> newtokens = accessToken.getTokens();
			for (Iterator<TokenUser> iterator = newtokens.iterator(); iterator.hasNext();) {
				TokenUser tokenUser = (TokenUser) iterator.next();
				if (!tokenHelper.validateToken(tokenUser.getToken())) {
					iterator.remove();
				}
			}
			newtokens.add(new TokenUser(jwt));
			accessToken.setTokens(newtokens);
			tokenRepository.save(accessToken);
		}

		return new LoginResponse(userResponse, jwt, EXPIRES_IN);
	}
}
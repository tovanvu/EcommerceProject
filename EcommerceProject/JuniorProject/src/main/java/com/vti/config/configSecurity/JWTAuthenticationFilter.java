package com.vti.config.configSecurity;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.vti.model.entity.AccessToken;
import com.vti.model.entity.TokenUser;
import com.vti.repository.IRedisTokenRepository;
import com.vti.service.login.LoginUserDetailService;
import com.vti.ultils.TokenHelper;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	
	Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);
	
	@Autowired
	private LoginUserDetailService loginUserDetailService;

	@Autowired
	private TokenHelper jwttokenHelper;

	@Autowired
	private IRedisTokenRepository tokenRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			// Lấy jwt từ request
			String jwt1 = getJwtFromRequest(request);
			AccessToken accessToken = new AccessToken();
			
			if (StringUtils.hasText(jwt1) && jwttokenHelper.validateToken(jwt1)) {
				String username1 = jwttokenHelper.getUsernameFromToken(jwt1);
				accessToken = tokenRepository.findById(username1).orElse(null);
			}
			List<TokenUser> tokenUsers = accessToken.getTokens();

			String jwt = null;

			if (tokenUsers != null) {
				for (TokenUser tokenUser : tokenUsers) {
					if (jwt1.equals(tokenUser.getToken())) {
						jwt = tokenUser.getToken();
					}
				}
			}

			if (StringUtils.hasText(jwt) && jwttokenHelper.validateToken(jwt)) {
				// Lấy username user từ chuỗi jwt
				String username = jwttokenHelper.getUsernameFromToken(jwt);

				// Lấy thông tin người dùng từ username
				UserDetails userDetails = loginUserDetailService.loadUserByUsername(username);
				if (userDetails != null) {
					// Nếu người dùng hợp lệ, set thông tin cho Seturity Context
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}

		} catch (Exception ex) {
			log.info("failed on set user authentication", ex);
		}

		chain.doFilter(request, response);
	}

	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		// Kiểm tra xem header Authorization có chứa thông tin jwt không
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
			return bearerToken.substring(7);
		}
		return null;
	}

}

package com.yourl.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class CustomAuthenticationManager implements AuthenticationManager {

	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub
		// return null;

		String username = authentication.getPrincipal() + "";
		String password = authentication.getCredentials() + "";

		// User user = userRepo.findOne(username);
		// if (user == null) {
		// throw new BadCredentialsException("1000");
		// }
		// if (user.isDisabled()) {
		// throw new DisabledException("1001");
		// }
		// if (!encoder.matches(password, user.getPassword())) {
		// throw new BadCredentialsException("1000");
		// }
		// List<Right> userRights = rightRepo.getUserRights(username);
		return new UsernamePasswordAuthenticationToken(username, password,
				userRights.stream()
						.map(x -> new SimpleGrantedAuthority(x.getName()))
						.collect(Collectors.toList()));
	}

}

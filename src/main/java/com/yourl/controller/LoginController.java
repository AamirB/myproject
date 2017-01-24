package com.yourl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yourl.dto.AccountCreateResponse;
import com.yourl.service.AccountService;

@RestController
@RequestMapping("/login")
public class LoginController {

	private static final Logger LOG = LoggerFactory
			.getLogger(LoginController.class);

	@Autowired
	private AccountService registrationService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@RequestMapping(value = "/{accountId}/{password}", method = RequestMethod.POST)
	public AccountCreateResponse login(@PathVariable String accountId,
			@PathVariable String password, HttpServletRequest request) {
		LOG.info("Request to login user with accountId:{0} ", accountId);
		// authenticate user

		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				accountId, password);

		// Authenticate the user

		Authentication authentication = new Authentication();
		authentication.s

//		Authentication authentication = authenticationManager
//				.authenticate(authRequest);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		securityContext.setAuthentication(authentication);
		// securityContext.setAuthentication(authRequest).
		// ((Object)
		// SecurityContextHolder.getContext()).setPrincipal(accountId);

		// Create a new session and add the security context.
		HttpSession session = request.getSession(true);
		session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

		// create session

		try {
			return registrationService.createAccount(accountId);

		} catch (Exception e) {
			LOG.error("Error while creating account: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage() != null
					? e.getMessage()
					: "Error in creating account");
		}

	}

}

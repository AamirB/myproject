package com.yourl.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yourl.service.AccountService;

@RestController

@RequestMapping("/login")

public class LoginController {

	private static final Logger LOG = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private AccountService registrationService;

	@RequestMapping(value = "/{accountId}/{password}", method = RequestMethod.POST)

	public boolean login(@PathVariable String accountId, @PathVariable String password, HttpServletRequest request,
			HttpServletResponse response) {
		LOG.info("Request to login user with accountId:{} ", accountId);
		try {
			return registrationService.authenticateUser(accountId, password, request, response);
		} catch (Exception e) {
			LOG.error("Error while creating account: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage() != null ? e.getMessage() : "Error in creating account");
		}

	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)

	public void logOut(HttpServletRequest request, HttpServletResponse response) {
		LOG.info("Request to login out");
		try {
			registrationService.logoutUser(request, response);
		} catch (Exception e) {
			LOG.error("Error while log out account: {}", e.getMessage(), e);
			throw new RuntimeException(e.getMessage() != null ? e.getMessage() : "Error in creating account");

		}

	}

}

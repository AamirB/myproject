package com.yourl.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yourl.dto.AccountCreateResponse;
import com.yourl.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	private static final Logger LOG = LoggerFactory
			.getLogger(AccountController.class);

	@Autowired
	private AccountService registrationService;

	@RequestMapping(value = "/{accountId}", method = RequestMethod.POST)
	public AccountCreateResponse register(@PathVariable String accountId) {
		LOG.info("Request to create user with accountId:{0}", accountId);

		// Principal p;

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

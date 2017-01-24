package com.yourl.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yourl.dto.AccountCreateResponse;

@Service
public class AccountServiceImpl implements AccountService {

	private Map<String, String> userById = new ConcurrentHashMap<>();

	@Autowired
	public PassWordGeneratorService passWordGeneratorService;

	@Override
	public String findUserById(String id) {
		return userById.get(id);
	}

	@Override
	public void storeUserPwdAndId(String id, String pwd) {
		userById.put(id, pwd);

	}

	@Override
	public AccountCreateResponse createAccount(String id) {
		AccountCreateResponse res = new AccountCreateResponse();

		if (findUserById(id) == null) {
			String passwrd = passWordGeneratorService.getSaltString();
			storeUserPwdAndId(id, passwrd);
			res.setDescription("Successfully created account with password " + passwrd);
			res.setSuccess(true);
			res.setPassword(passwrd);

		} else {
			res.setDescription("Account already exist with ID " + id);
			res.setSuccess(false);
		}
		return res;

	}

	@Override
	public boolean authenticateUser(String userId, String password) {

		if (userById.get(userId) == null) {
			return false;
		} else if (userById.get(userId).equals(password)) {
			return true;
		}
		return false;
	}

}

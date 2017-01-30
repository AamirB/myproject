package com.yourl.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yourl.dto.AccountCreateResponse;

public interface AccountService {
	String findUserById(String id);

	void storeUserPwdAndId(String id, String pwd);

	AccountCreateResponse createAccount(String id);

	boolean authenticateUser(String userId, String password);

	boolean authenticateUser(String accountId, String password,
			HttpServletRequest request, HttpServletResponse response);

	void setLoggedInuser(String accountId);

	void logoutUser(HttpServletRequest request, HttpServletResponse response);

	String getLoggedInuser();

	boolean authenticate(String authCredentials);
}

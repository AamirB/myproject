package com.yourl.service;

import com.yourl.dto.AccountCreateResponse;

public interface AccountService {
	String findUserById(String id);

	void storeUserPwdAndId(String id, String pwd);

	AccountCreateResponse createAccount(String id);

	boolean authenticateUser(String userId, String password);
}

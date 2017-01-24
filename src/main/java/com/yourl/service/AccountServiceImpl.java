package com.yourl.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yourl.dto.AccountCreateResponse;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AccountServiceImpl implements AccountService {

	private Map<String, String> userById = new ConcurrentHashMap<>();

	@Autowired
	public PassWordGeneratorService passWordGeneratorService;

	private Map<String, String> currentSession = new HashMap<String, String>();

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
			res.setDescription(
					"Successfully created account with password " + passwrd);
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

	@Override
	public boolean authenticateUser(String accountId, String password,
			HttpServletRequest request, HttpServletResponse response) {

		if (authenticateUser(accountId, password)) {
			try {
				String jwt = Jwts.builder().setSubject("users/TzMUocMF4p")
						.claim("username", accountId)
						.signWith(SignatureAlgorithm.HS256,
								"secret".getBytes("UTF-8"))
						.compact();
				Cookie cookie = new Cookie("token", jwt);
				cookie.setMaxAge(60 * 60 * 24 * 365);
				response.setContentType("text/html");
				cookie.setPath("/");
				response.addCookie(cookie);
				setLoggedInuser(accountId);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}

	@Override
	public void setLoggedInuser(String accountId) {
		currentSession.put("user", accountId);

	}

	@Override
	public String getLoggedInuser() {
		return currentSession.get("user");

	}

	@Override
	public void logoutUser(HttpServletRequest request,
			HttpServletResponse response) {
		currentSession.remove("user");

	}

}

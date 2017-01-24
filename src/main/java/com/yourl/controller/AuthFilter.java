package com.yourl.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yourl.service.AccountService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class AuthFilter implements Filter {

	@Autowired
	private AccountService accountService;

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		Cookie cookies[] = request.getCookies();
		String cookie = null;
		for (Cookie Cookie : cookies) {
			if ("token".equals(Cookie.getName())) {
				cookie = Cookie.getValue();
			}
		}
		String jwttoken = cookie;
		Jws<Claims> claims = null;
		try {
			claims = Jwts.parser().setSigningKey("secret".getBytes("UTF-8"))
					.parseClaimsJws(jwttoken);
		} catch (ExpiredJwtException e1) {
			e1.printStackTrace();
		} catch (UnsupportedJwtException e1) {
			e1.printStackTrace();
		} catch (MalformedJwtException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e1) {
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		String scope = (String) claims.getBody().get("username");
		// System.out.println(scope);
		if (scope != null) {
			accountService.setLoggedInuser(scope);
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy() {
	}
}
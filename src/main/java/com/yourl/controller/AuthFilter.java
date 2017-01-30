package com.yourl.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yourl.service.AccountService;

@Component
public class AuthFilter implements Filter {

	@Autowired
	private AccountService accountService;

	public static final String AUTHENTICATION_HEADER = "Authorization";

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filter) throws IOException, ServletException {

		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String authCredentials = httpServletRequest
					.getHeader(AUTHENTICATION_HEADER);

			// TODO logic on URI
			System.out.println(httpServletRequest.getRequestURL());
			System.out.println(httpServletRequest.getRequestURI());
			String uri = httpServletRequest.getRequestURI();
			if (authCredentials == null || uri.contains("/login")
					|| uri.contains("/account")) {
				filter.doFilter(request, response);
			} else {

				boolean authenticationStatus = accountService
						.authenticate(authCredentials);

				if (authenticationStatus) {
					filter.doFilter(request, response);
				} else {
					if (response instanceof HttpServletResponse) {
						HttpServletResponse httpServletResponse = (HttpServletResponse) response;
						httpServletResponse
								.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					}
				}
			}
		}
	}

	@Override
	public void destroy() {
	}
}
package com.yourl.controller;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yourl.dto.ShortUrlResponse;
import com.yourl.dto.StatsURlInfo;
import com.yourl.dto.UrlInfo;
import com.yourl.service.IUrlStoreService;
import com.yourl.service.UrlService;

@Controller
@RestController
@RequestMapping("/url")
public class UrlController {

	@Autowired
	private UrlService urlService;

	@Autowired
	private IUrlStoreService urlStoreService;

	@RequestMapping(value = "/redirect", method = RequestMethod.GET)
	public void redirectToUrl(String id, HttpServletResponse resp)
			throws Exception {
		final UrlInfo url = urlStoreService.findUrlById(id);
		if (url != null) {
			resp.addHeader("Location", url.getUrl().toString());
			resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
		} else {
			resp.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@RequestMapping(value = "/shorten/{redirectionType}", method = RequestMethod.POST)
	public ShortUrlResponse shortenUrl(String url,
			@PathVariable String redirectionType) {

		if (!isUrlValid(url)) {
			throw new RuntimeException("URL is not valid");
		}

		try {
			return urlService.shortenUrl(url, redirectionType);
		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage() != null
					? ex.getMessage()
					: "Error occured in shortening the URL");
		}

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public void shortenUrl(@PathVariable String id, HttpServletResponse resp) {

		final UrlInfo url = urlStoreService.findUrlById(id);

		if (!isUrlValid(url.getUrl())) {
			throw new RuntimeException("URL is not valid");
		}

		try {
			resp.addHeader("Location", url.getUrl().toString());
			resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);

		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage() != null
					? ex.getMessage()
					: "Error occured in shortening the URL");
		}

	}

	@RequestMapping(value = "/showStats", method = RequestMethod.GET)
	public @ResponseBody List<StatsURlInfo> showStats() {

		try {
			return urlStoreService.findAllStats();

		} catch (Exception ex) {
			throw new RuntimeException(ex.getMessage() != null
					? ex.getMessage()
					: "Error occured in getting stats of the URL");
		}

	}

	private boolean isUrlValid(String url) {
		boolean valid = true;
		try {
			new URL(url);
		} catch (MalformedURLException e) {
			valid = false;
		}
		return valid;
	}
}

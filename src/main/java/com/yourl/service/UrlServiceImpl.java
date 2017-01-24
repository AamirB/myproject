package com.yourl.service;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.hash.Hashing;
import com.yourl.dto.ShortUrlResponse;
import com.yourl.dto.UrlInfo;

@Service
public class UrlServiceImpl implements UrlService {

	@Autowired
	private IUrlStoreService urlStoreService;

	@Autowired
	private AccountService registrationService;

	// TODO Externalize
	private static final String serverUrl = "http://localhost:8080/";

	@Override
	public ShortUrlResponse shortenUrl(String url, String redirectionType) {

		ShortUrlResponse res = new ShortUrlResponse();

		try {
			// check if its already shortend

			if (urlStoreService.alreadyShortened(url) != null) {
				return res;
			}

			final String id = Hashing.murmur3_32()
					.hashString(url, StandardCharsets.UTF_8).toString();
			UrlInfo urlInfo = urlStoreService.findUrlById(id);
			if (urlInfo == null) {
				urlInfo = new UrlInfo();
				urlInfo.setCount(1);
				urlInfo.setUrl(url);
				urlInfo.setAccountId(registrationService.getLoggedInuser());
				urlStoreService.storeUrl(id, urlInfo);
			} else if (urlInfo.getAccountId() != null) {
				urlInfo.setCount(urlInfo.getCount() + 1);
			}
			StringBuilder sb = new StringBuilder();
			String urlFinal = sb.append(serverUrl).append("url/").append(id)
					.toString();
			res.setShortUrl(urlFinal);

		} catch (Exception ex) {

		}

		return res;
	}

}

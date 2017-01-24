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

	// TODO Externalize
	private static final String serverUrl = "http://localhost:8080/";

	@Override
	public ShortUrlResponse shortenUrl(String url, String redirectionType) {

		ShortUrlResponse res = new ShortUrlResponse();

		try {
			// TODO set the account to list f URLINFO maapping
			final String id = Hashing.murmur3_32()
					.hashString(url, StandardCharsets.UTF_8).toString();
			UrlInfo urlInfo = urlStoreService.findUrlById(id);
			if (urlInfo == null) {
				urlInfo = new UrlInfo();
				urlInfo.setCount(1);
				urlInfo.setUrl(url);
				urlStoreService.storeUrl(id, urlInfo);
			} else {
				urlInfo.setCount(urlInfo.getCount() + 1);
			}

			StringBuilder sb = new StringBuilder();
			String urlFinal = sb.append(serverUrl).append("url/").append(id)
					.toString();
			urlStoreService.storeUrl(urlFinal, urlInfo);
			res.setShortUrl(urlFinal);

		} catch (Exception ex) {

		}

		return res;
	}

}

package com.yourl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yourl.dto.ShortUrlResponse;
import com.yourl.dto.StatsURlInfo;
import com.yourl.dto.UrlInfo;

@Service
public class MemUrlStoreService implements IUrlStoreService {
	private Map<String, UrlInfo> urlByIdMap = new HashMap<String, UrlInfo>();

	@Autowired
	private AccountService registrationService;

	// TODO Externalize
	private static final String serverUrl = "http://localhost:8080/";

	@Override
	public UrlInfo findUrlById(String id) {
		return urlByIdMap.get(id);
	}

	@Override
	public void storeUrl(String id, UrlInfo url) {
		urlByIdMap.put(id, url);
	}

	@Override
	public List<StatsURlInfo> findAllStats() {
		List<StatsURlInfo> statsList = new ArrayList<StatsURlInfo>();
		String loggedInUser = registrationService.getLoggedInuser();

		if (loggedInUser == null) {
			return statsList;
		}

		for (Map.Entry<String, UrlInfo> e : urlByIdMap.entrySet()) {
			UrlInfo user = e.getValue();
			if (loggedInUser.equals(user.getAccountId())) {
				StatsURlInfo s = new StatsURlInfo();
				s.setCount(user.getCount());
				s.setUrl(user.getUrl());
				statsList.add(s);
			}

		}

		return statsList;
	}

	@Override
	public ShortUrlResponse alreadyShortened(String url) {

		String loggedInUser = registrationService.getLoggedInuser();
		if (loggedInUser == null) {
			return null;
		}
		ShortUrlResponse res = new ShortUrlResponse();

		for (Map.Entry<String, UrlInfo> e1 : urlByIdMap.entrySet()) {
			UrlInfo user = e1.getValue();
			if (loggedInUser.equals(user.getAccountId())
					&& url.equals(user.getUrl())) {
				user.setCount(user.getCount());

				StringBuilder sb = new StringBuilder();
				String urlFinal = sb.append(serverUrl).append("url/")
						.append(e1.getKey()).toString();
				res.setShortUrl(urlFinal);
				return res;
			}

		}
		return null;

	}

}

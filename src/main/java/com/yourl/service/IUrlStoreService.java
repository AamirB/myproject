package com.yourl.service;

import java.util.List;

import com.yourl.dto.ShortUrlResponse;
import com.yourl.dto.StatsURlInfo;
import com.yourl.dto.UrlInfo;

public interface IUrlStoreService {
	UrlInfo findUrlById(String id);

	void storeUrl(String id, UrlInfo url);

	List<StatsURlInfo> findAllStats();

	ShortUrlResponse alreadyShortened(String url);
}

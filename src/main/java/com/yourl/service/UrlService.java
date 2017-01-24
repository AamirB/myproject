package com.yourl.service;

import com.yourl.dto.ShortUrlResponse;

public interface UrlService {
	ShortUrlResponse shortenUrl(String url, String redirectionType);
}

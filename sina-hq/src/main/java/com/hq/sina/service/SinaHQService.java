package com.hq.sina.service;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by Allen.Wang on 2016/12/10.
 */
@Service
public class SinaHQService {
	private static final Logger LOG = LoggerFactory.getLogger(SinaHQService.class);
	private static final String URL = "http://hq.sinajs.cn/list=sh204001,sh204002,sh204003,sh204004,sh204007,sz131810,sz131811,sz131800,sz131809,sz131801";
	private static final float MIN_VALUE = 6;

	public String spiderHQ() throws Exception {
		String message = "";
		String[] lines = getUrlResponse(URL).body().split("\n");
		for (String line : lines) {
			String[] items = line.split(",");
			if (Float.valueOf(items[3]).floatValue() >= MIN_VALUE) {
				message = message + items[0].substring(21) + ",当前价格:" + items[3] + "\n";
			}
		}
		return message.trim();
	}

	public Response getUrlResponse(String url) throws Exception {
		Response response = Jsoup.connect(url)
				.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0")
				.timeout(10000).method(Method.GET).ignoreContentType(true).ignoreHttpErrors(true).followRedirects(false)
				.execute();
		return response;
	}

}

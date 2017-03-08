package com.hq.sina.schduling;

import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.hq.sina.service.SinaHQService;
import com.hq.sina.ui.VersionUtil;

@Component
public class SinaHQScheduler {
	private static final Logger LOG = LoggerFactory.getLogger(SinaHQScheduler.class);

	@Autowired
	private SinaHQService service;

	private AtomicBoolean executed = new AtomicBoolean(true);

	@Scheduled(fixedDelay = 10 * 1000)
	private void scanPer10Second() {
		if (executed.getAndSet(false))
			try {
				String hq = service.spiderHQ();
				if (!StringUtils.isEmpty(hq))
					new VersionUtil(hq);
			} catch (Exception e) {
				LOG.error("spide error!", e);
			} finally {
				executed.set(true);
			}
	}
}

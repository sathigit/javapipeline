package com.etree.rts.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.etree.rts.service.product.ProductService;

@Configuration
@EnableScheduling
public class ScheduledJobDaily12AM {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledJobDaily12AM.class);
	@Autowired
	ProductService productService;

	@Scheduled(cron = "${scheduling.job.cron.daily}")
	public void run() {
		try {
			//productService.syncProducts();
		} catch (Exception e) {
			logger.error("Exception in addProducts", e);
		}
	}
}

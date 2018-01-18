package com.etree.rts.cron;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.etree.rts.service.product.ProductService;

public class SyncTask implements Job {

	private static final Logger logger = LoggerFactory.getLogger(SyncTask.class);
	@Autowired
	ProductService productService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			logger.info("Products Sync Started");
			//productService.syncProducts();
			logger.info("Products Sync Ended");
		} catch (Exception e) {
			logger.error("Exception in addProducts", e);
		}
	}

}
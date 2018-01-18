package com.etree.rts.cron;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.etree.rts.dao.user.UserDAO;
import com.etree.rts.domain.datasync.DataSync;
import com.etree.rts.domain.user.User;
import com.etree.rts.service.product.ProductService;

public class JobSync implements Job {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledCron.class);
	@Autowired
	UserDAO userDAO;
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("Products Sync Started user id :" + context.getTrigger().getKey().getName());
		try {
			JobDataMap data = context.getMergedJobDataMap();
			DataSync dataSync = (DataSync) data.get("dataSync");
			ProductService productService = (ProductService) data.get("productService");
			User user = userDAO.getUser(dataSync.getUserId());
			productService.syncProductsIntermediate(user.getOrganizationId());
		} catch (Exception e) {
			logger.error("Exception in execute", e);
		}
		logger.info("Products Sync Ended");
	}

}
package com.etree.rts.cron;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.etree.rts.dao.datasync.DataSyncDAO;
import com.etree.rts.dao.user.UserDAO;
import com.etree.rts.domain.datasync.DataSync;
import com.etree.rts.domain.user.User;
import com.etree.rts.service.product.ProductService;

@EnableScheduling
@Service
@Component
@Scope("prototype")
public class ScheduledJob implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledJob.class);
	@Autowired
	ProductService productService;
	@Autowired
	TaskScheduler taskScheduler;
	@Autowired
	DataSyncDAO dataSyncDAO;
	@Autowired
	UserDAO userDAO;

	private DataSync data;

	public DataSync getData() {
		return data;
	}

	public void setData(DataSync data) {
		this.data = data;
	}

	public void processScheduledJob(DataSync data) {
		try {
			this.setData(data);
			taskScheduler.schedule(this, new CronTrigger(data.getCronExpression()));
		} catch (Exception e) {
			logger.error("Exception in processScheduledJob", e);
		}
	}

	@Override
	public void run() {
		try {
			logger.info("Products Sync Started user id :" + getData().getUserId());
			User user = userDAO.getUser(getData().getUserId());
			productService.syncProductsIntermediate(user.getOrganizationId());
			logger.info("Products Sync Ended");
		} catch (Exception e) {
			logger.error("Exception in addProducts", e);
		}
	}
}

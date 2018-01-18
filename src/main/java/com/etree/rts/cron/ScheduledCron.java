package com.etree.rts.cron;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.etree.rts.dao.datasync.DataSyncDAO;
import com.etree.rts.domain.datasync.DataSync;
import com.etree.rts.service.product.ProductService;

@EnableScheduling
@Service
@Component
public class ScheduledCron {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledCron.class);
	@Autowired
	ProductService productService;
	@Autowired
	TaskScheduler taskScheduler;
	@Autowired
	DataSyncDAO dataSyncDAO;
	private Scheduler scheduler;

	public void processScheduledJob() {
		try {
			List<DataSync> datas = dataSyncDAO.getData();
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			JobDataMap parameters = new JobDataMap();
			parameters.put("productService", productService);
			for (DataSync data : datas) {
				parameters.put("dataSync", data);
				JobKey jobKeyA = new JobKey(data.getName(), "rtsGroup");
				JobDetail jobDetail = JobBuilder.newJob(JobSync.class).withIdentity(jobKeyA).build();
				scheduler.deleteJob(jobKeyA);
				Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity(data.getName(), "rtsGroup")
						.usingJobData(parameters)
						.withSchedule(CronScheduleBuilder.cronSchedule(data.getCronExpression())).build();
				scheduler.scheduleJob(jobDetail, trigger1);

			}
		} catch (Exception e) {
			logger.error("Exception in processScheduledJob", e);
		}
	}

}

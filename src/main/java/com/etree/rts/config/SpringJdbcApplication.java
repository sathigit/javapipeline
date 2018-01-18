package com.etree.rts.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.etree.rts.cron.ScheduledCron;
import com.etree.rts.cron.ScheduledJob;
import com.etree.rts.dao.datasync.DataSyncDAO;

@SpringBootApplication(scanBasePackages = { "com.etree.rts" })
public class SpringJdbcApplication {
	@Autowired
	public ScheduledJob scheduledJob;

	@Autowired
	public ScheduledCron scheduledCron;

	@Autowired
	DataSyncDAO dataSyncDAO;

	public static void main(String[] args) {
		SpringApplication.run(SpringJdbcApplication.class, args);
	}

	@PostConstruct
	public void init() throws Exception {
		scheduledCron.processScheduledJob();
	}

	@Bean
	public SseEmitter emitter() {
		return new SseEmitter();
	}

	@Bean
	public TaskScheduler taskScheduler() {
		return new ConcurrentTaskScheduler();
	}
}

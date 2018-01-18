package com.etree.rts.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) {
		logger.info("getOverallReportsForCampaign: Received request: " + request.getRequestURL().toString());
		logger.info("API controler is up & running...", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);
		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@ResponseBody
	@RequestMapping(value = "/healthcheck", method = RequestMethod.GET)
	public String display(HttpServletRequest request, HttpServletResponse response) {
		logger.info("healthcheck: Received request: " + request.getRequestURL().toString());
		response.setStatus(HttpServletResponse.SC_OK);
		logger.info("healthcheck: Sent response");
		return "{ \"status\" : \"ok\" } ";
	}

}

package com.etree.rts.controller.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/v1")
public class OrderSyncController {

	@Autowired
	SseEmitter sseEmitter;

	@RequestMapping(path = "/sync/order/status", method = RequestMethod.GET)
	public SseEmitter syncOrderStatus() {
		return sseEmitter;
	}

}
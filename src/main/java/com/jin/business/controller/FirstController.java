package com.jin.business.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jin.business.service.FirstService;

@RestController
public class FirstController {
	private static final Logger logger = LoggerFactory.getLogger(FirstController.class);
	
	@Autowired
	FirstService firstService;
	
	@RequestMapping("/send")
	public void sendMessage(){
		firstService.sendMessage();
	}

}

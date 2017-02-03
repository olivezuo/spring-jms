package com.jin.business.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.jin.queue.service.JmsMessageSendService;

@Service
@ConfigurationProperties(prefix = "com.jin.producer.first")
public class FirstService {

	private static final Logger logger = LoggerFactory.getLogger(FirstService.class);
	
	@Autowired
	JmsMessageSendService jmsMessageSendService;
	
	private String queueName;
	
	public void printMessage(String message) {
		logger.info("We receive a message :" + message);
		
	}
	
	public void sendMessage(){
		jmsMessageSendService.send(queueName, "This is just a testing message");
	}
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

}

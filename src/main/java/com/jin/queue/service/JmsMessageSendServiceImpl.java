package com.jin.queue.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jin.queue.config.JmsConfig;

@Service
public class JmsMessageSendServiceImpl implements JmsMessageSendService {
	private static final Logger logger = LoggerFactory.getLogger(JmsMessageSendServiceImpl.class);
	
	@Autowired
	JmsConfig jmsConfig;
	
	public void send(String queueName, String message) {
		jmsConfig.mqJmsTemplate().convertAndSend(queueName, message);
		logger.info("Send message successfully. The messge is " + message);
		
	}
}

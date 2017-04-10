package com.jin.business.service;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.jms.core.MessagePostProcessor;
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
		
		Map<String, Object> headers = new HashMap<String, Object>();
		headers.put("testHeader", "Test HeadersFirst");
		headers.put("correlationId", "123-456");
		
		
		jmsMessageSendService.send(queueName, "This is messaging message", messagePostProcessor(headers));
	}
	
	private MessagePostProcessor messagePostProcessor(Map<String, Object> headers) {
		return new MessagePostProcessor(){

			@Override
			public javax.jms.Message postProcessMessage(javax.jms.Message message) throws JMSException {
				message.setStringProperty("testHeader", (String)headers.get("testHeader"));
				message.setJMSCorrelationID((String)headers.get("correlationId"));
				return message;
			}
			
		};
	}
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

}

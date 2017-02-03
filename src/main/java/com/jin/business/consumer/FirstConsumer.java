package com.jin.business.consumer;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import com.jin.business.service.FirstService;
import com.jin.queue.consumer.AbsJmsMessageConsumer;

@Service
@ConfigurationProperties(prefix = "com.jin.consumer.first")
public class FirstConsumer extends AbsJmsMessageConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(FirstConsumer.class);
	@Autowired
	FirstService firstService;
	
	@PostConstruct
	public void init() {
		logger.info("----Initialize the First Consumer---");
		this.startDefaultMessageListenerContainer(queueName,  maxConcurrentConsumers, concurrentConsumers);
	}
	
	@Override
	protected <T> T getMessageObj(T decodedMessage) {		
		return decodedMessage;
	}

	@Override
	protected <T> void process(T message) throws Exception {
		firstService.printMessage((String)message);
	}

}

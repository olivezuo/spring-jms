package com.jin.queue.service;

import javax.jms.JMSException;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

import com.jin.queue.config.JmsConfig;

@Service
public abstract class JmsMessageReceiveServiceImpl implements JmsMessageReceiveService {

	private static final Logger logger = LoggerFactory.getLogger(JmsMessageReceiveServiceImpl.class);
	
	@Autowired
	protected JmsConfig jmsConfig;

	protected String queueName;
	protected int maxConcurrentConsumers = 10;
	protected int concurrentConsumers = 3; 

	protected DefaultMessageListenerContainer defaultMessageListenerContainer;
	
	protected void startDefaultMessageListenerContainer(String queueName,int maxConcurrentConsumers, int concurrentConsumers){
		initDefaultMessageListenerContainer(queueName,maxConcurrentConsumers, concurrentConsumers);
		logger.info("---Create the Default Message listener Container---");
		defaultMessageListenerContainer.initialize();
		defaultMessageListenerContainer.start();
	}
	
	protected void initDefaultMessageListenerContainer (String queueName,int maxConcurrentConsumers, int concurrentConsumers) {
		defaultMessageListenerContainer = new DefaultMessageListenerContainer();
		defaultMessageListenerContainer.setConnectionFactory(jmsConfig.mqbmConnectionFactory());
		defaultMessageListenerContainer.setDestinationName(queueName);
		defaultMessageListenerContainer.setMessageListener(sessionAwareMessageListener());
		defaultMessageListenerContainer.setSessionAcknowledgeMode(Session.AUTO_ACKNOWLEDGE);
		defaultMessageListenerContainer.setConcurrentConsumers(concurrentConsumers);
		defaultMessageListenerContainer.setMaxConcurrentConsumers(maxConcurrentConsumers);
		defaultMessageListenerContainer.setReceiveTimeout(1000);
		defaultMessageListenerContainer.setTransactionTimeout(2);
	}
	
	protected MessageListenerAdapter sessionAwareMessageListener() {
		return new MessageListenerAdapter() {
			@Override
			public void onMessage(javax.jms.Message message, Session session) throws JMSException {
				Message<?> messagingMessage = (Message<?>)this.getMessagingMessageConverter().fromMessage(message);
				receive(messagingMessage, session);
				//session.close();
			}
		};
	}	
	
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public int getMaxConcurrentConsumers() {
		return maxConcurrentConsumers;
	}

	public void setMaxConcurrentConsumers(int maxConcurrentConsumers) {
		this.maxConcurrentConsumers = maxConcurrentConsumers;
	}

	public int getConcurrentConsumers() {
		return concurrentConsumers;
	}

	public void setConcurrentConsumers(int concurrentConsumers) {
		this.concurrentConsumers = concurrentConsumers;
	}

}

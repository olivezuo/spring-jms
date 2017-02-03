package com.jin.queue.consumer;

import java.io.IOException;

import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

import com.jin.queue.service.JmsMessageReceiveServiceImpl;

public abstract class AbsJmsMessageConsumer extends JmsMessageReceiveServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(AbsJmsMessageConsumer.class);
		
	protected abstract <T> T getMessageObj(T decodedMessage);
	
	protected abstract <T> void process(T message) throws Exception;

	@Override
	public void receive(Message message, Session session) {
		Object messageObj = null;
		try{
			logger.info("The received message is " + message.toString());
			Object decodedMessage = jmsConfig.mqJmsTemplate().getMessageConverter().fromMessage(message);
			messageObj = getMessageObj(decodedMessage);
		
			process(messageObj);
		} catch(IOException e1){
				logger.error("Failed to Acknowledge message with type: " + messageObj.getClass().getName() + ". The message is: " + messageObj.toString() + " The Exceptions is:  " + e1.getMessage(),e1);
				return;
		} catch (Exception e) {
			logger.error("Failed to process the message with type: " + messageObj.getClass().getName() + ". The message is: " + messageObj.toString() + " The Exceptions is:  " + e.getMessage(),e);
			//retry(decodedMessage, messageProperites, e.getMessage());
		}
	}

}

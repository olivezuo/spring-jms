package com.jin.queue.service;

import org.springframework.jms.core.MessagePostProcessor;

public interface JmsMessageSendService {

	public <T> void send(String queueName, T message, MessagePostProcessor messagePostProcessor);
}

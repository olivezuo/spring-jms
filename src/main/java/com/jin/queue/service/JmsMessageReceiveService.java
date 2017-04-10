package com.jin.queue.service;

import javax.jms.Session;

import org.springframework.messaging.Message;

public interface JmsMessageReceiveService {

	public void receive(Message<?> message, Session session);

}

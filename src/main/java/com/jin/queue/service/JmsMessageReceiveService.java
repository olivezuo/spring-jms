package com.jin.queue.service;

import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.jms.listener.SimpleMessageListenerContainer;

public interface JmsMessageReceiveService {

	public void receive(Message message, Session session);

}

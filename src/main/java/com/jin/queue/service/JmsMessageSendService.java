package com.jin.queue.service;

public interface JmsMessageSendService {

	public void send(String queueName, String message);
}

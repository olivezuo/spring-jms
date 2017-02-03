package com.jin.queue.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

import com.ibm.msg.client.mqlight.factories.MQBMConnectionFactory;

@Configuration
@ConfigurationProperties(prefix = "com.jin.jms")
public class JmsConfig {

	private String queueManager;
	private String host;
	private String channel;
	private int port;

	@Bean
	public JmsTemplate mqJmsTemplate() {
		JmsTemplate jmsTemplate = new JmsTemplate();
		jmsTemplate.setConnectionFactory(mqbmConnectionFactory());
		return jmsTemplate;
	}

	@Bean
	public MQBMConnectionFactory mqbmConnectionFactory() {
		MQBMConnectionFactory mqbmConnectionFactory = new MQBMConnectionFactory();
		mqbmConnectionFactory.setHostName(host);
		try {
			mqbmConnectionFactory.setTransportType(1);
			mqbmConnectionFactory.setHostName(host);
			mqbmConnectionFactory.setChannel(channel);
			mqbmConnectionFactory.setPort(port);
			mqbmConnectionFactory.setQueueManager(queueManager);
			mqbmConnectionFactory.setClientReconnectTimeout(10);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mqbmConnectionFactory;
	}

	public String getQueueManager() {
		return queueManager;
	}

	public void setQueueManager(String queueManager) {
		this.queueManager = queueManager;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

}

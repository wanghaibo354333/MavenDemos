package com.xuexi.activemq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsProduce {
	
	private static final String ACTIVEMQ_RL="tcp://192.168.88.128:61616";
	private static String queueName="queue01";

	public static void main(String[] args) throws JMSException {
		//创建连接工厂 设置指定的url 用户名密码默认
		ActiveMQConnectionFactory activeMQConnectionFactory= new ActiveMQConnectionFactory(ACTIVEMQ_RL);
	    //获得连接Connection 启动访问
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();
		//创建回话session
		//第一个值是事物  第二个是签收
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		//创建目的地
		Queue queue = session.createQueue(queueName);
		//创建消息的生产者
		MessageProducer messageProducer = session.createProducer(queue);
		//发布三条消息到队列
		for (int i = 1; i <= 3; i++) {
			TextMessage textMessage = session.createTextMessage("msg======"+i);
			messageProducer.send(textMessage);
		}
		messageProducer.close();
		session.close();
		connection.close();
		
		System.err.println("MQ消息发送完成------");
		
	}

}

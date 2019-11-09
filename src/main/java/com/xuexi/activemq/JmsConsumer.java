package com.xuexi.activemq;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;


public class JmsConsumer {
	private static final String ACTIVEMQ_RL="tcp://192.168.88.128:61616";
	private static String queueName="queue01";

	public static void main(String[] args) throws JMSException, IOException {
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
		//创建消费者
		MessageConsumer messageConsumer = session.createConsumer(queue);
		//同步阻塞
//		while (true) {
//			TextMessage textMessage = (TextMessage)messageConsumer.receive(4000L);
//			if (null!=textMessage) {
//				System.out.println("----消费者收到消息："+textMessage.getText());
//			}else {
//				break;
//			}			
//		}
		messageConsumer.setMessageListener(new MessageListener() {
			
			@Override
			public void onMessage(Message message) {
				// TODO Auto-generated method stub				
				if (null!=message&& message instanceof TextMessage) {
					TextMessage textMessage = (TextMessage)message;
					try {
						System.out.println("----消费者收到消息："+textMessage.getText());
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 
			}
		});
		
		System.in.read();
		messageConsumer.close();
		session.close();
		connection.close();		
		
	}
}

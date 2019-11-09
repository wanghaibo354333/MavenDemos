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
		//�������ӹ��� ����ָ����url �û�������Ĭ��
		ActiveMQConnectionFactory activeMQConnectionFactory= new ActiveMQConnectionFactory(ACTIVEMQ_RL);
	    //�������Connection ��������
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();
		//�����ػ�session
		//��һ��ֵ������  �ڶ�����ǩ��
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		//����Ŀ�ĵ�
		Queue queue = session.createQueue(queueName);
		//����������
		MessageConsumer messageConsumer = session.createConsumer(queue);
		//ͬ������
//		while (true) {
//			TextMessage textMessage = (TextMessage)messageConsumer.receive(4000L);
//			if (null!=textMessage) {
//				System.out.println("----�������յ���Ϣ��"+textMessage.getText());
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
						System.out.println("----�������յ���Ϣ��"+textMessage.getText());
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

package com.xuexi.activemq;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class JmsProduce_Topic {
	private static final String ACTIVEMQ_RL="tcp://192.168.88.128:61616";
	private static String TOPICNAME="topic_01";

	public static void main(String[] args) throws JMSException {
		//�������ӹ��� ����ָ����url �û�������Ĭ��
		ActiveMQConnectionFactory activeMQConnectionFactory= new ActiveMQConnectionFactory(ACTIVEMQ_RL);
	    //�������Connection ��������
		Connection connection = activeMQConnectionFactory.createConnection();
		connection.start();
		//�����ػ�session
		//��һ��ֵ������  �ڶ�����ǩ��
		Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
		//����Ŀ�ĵ�
		Topic topic = session.createTopic(TOPICNAME);
		//������Ϣ��������
		MessageProducer messageProducer = session.createProducer(topic);
		//����������Ϣ������
		for (int i = 1; i <= 3; i++) {
			TextMessage textMessage = session.createTextMessage("topic_msg----"+i);
			messageProducer.send(textMessage);
		}
		messageProducer.close();
		session.close();
		connection.close();
		
		System.err.println("MQ��Ϣ�������------");
		
	}

}
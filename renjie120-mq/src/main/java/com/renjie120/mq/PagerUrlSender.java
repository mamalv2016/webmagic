package com.renjie120.mq;
 

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 发送待解析的url字符串到消息队列中.
 * 
 * @author Administrator
 *
 */
public class PagerUrlSender {
	private static final int SEND_NUMBER = 5;

	
	private ConnectionFactory connf;
	private Connection conn = null;
	private Session session;
	private Destination des;
	private MessageProducer prod;
 
	public PagerUrlSender() {

		connf = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616"); 
		
	}

	public void sendMessage(String url){
		try {
			conn = connf.createConnection();
			conn.start();

			session = conn
					.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

			des = session.createQueue(MQConstants.QUEUENAME);

			prod = session.createProducer(des);

			prod.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			TextMessage message = session.createTextMessage(url); 
			prod.send(message); 

			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (JMSException e) { 
				e.printStackTrace();
			}
		}

	} 

}

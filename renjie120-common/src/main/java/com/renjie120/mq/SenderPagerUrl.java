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
public class SenderPagerUrl {
	private static final int SEND_NUMBER = 5;

	private static final String QUEUENAME = "needConsoleUrls";
	private ConnectionFactory connf;
	private Connection conn = null;
	private Session session;
	private Destination des;
	private MessageProducer prod;

	public SenderPagerUrl() {

		connf = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
				ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");

		try {
			conn = connf.createConnection();
			conn.start();

			session = conn
					.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

			des = session.createQueue(QUEUENAME);

			prod = session.createProducer(des);

			prod.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			sendMessage(session, prod);

			session.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void sendMessage(Session session, MessageProducer producer)
			throws Exception {
		for (int i = 1; i < SEND_NUMBER; i++) {
			TextMessage message = session.createTextMessage("测试发送消息" + i);
			System.out.println("mq端发送消息：" + i);
			producer.send(message);
		}
	}

}

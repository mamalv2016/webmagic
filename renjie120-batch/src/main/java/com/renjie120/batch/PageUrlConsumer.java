package com.renjie120.batch;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import com.renjie120.mq.MQConstants;

public class PageUrlConsumer {
	private static final String USERNAME=ActiveMQConnection.DEFAULT_USER;//默认用户名
	private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//默认密码
	private static final String BROKEURL=ActiveMQConnection.DEFAULT_BROKER_URL;//默认连接地址
	
	public static void main(String[] args){
		ConnectionFactory conf;
		Connection conn = null;
		
		Session session;
		
		Destination dest;
		
		MessageConsumer messageCon;
		
		conf = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKEURL);
		
		try{
			conn = conf.createConnection();
			conn.start();
			
			session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			dest = session.createQueue(MQConstants.QUEUENAME);
			
			messageCon = session.createConsumer(dest);
			
			while(true){
				TextMessage textmessage = (TextMessage)messageCon.receive(100000);
				if(textmessage!=null){
					System.out.println("收到消息:"+textmessage.getText());
				}else{
					break;
				}
			}
		} catch (JMSException e) {
            e.printStackTrace();
        }
	}
}

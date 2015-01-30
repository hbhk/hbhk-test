package org.hbhk.test.mq;

import java.util.concurrent.TimeUnit;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Receiver implements Runnable {

	public static void main(String[] args) {
		Receiver r = new Receiver();
		for (int i = 0; i < 3; i++) {
			Thread t = new Thread(r);
			t.start();
		}
	}

	public void run() {
		System.out.println("线程启动:" + Thread.currentThread().getName());
		// ConnectionFactory ：连接工厂，JMS 用它创建连接
		ConnectionFactory connectionFactory;
		// Connection ：JMS 客户端到JMS Provider 的连接
		Connection connection = null;
		// Session： 一个发送或接收消息的线程
		Session session;
		// Destination ：消息的目的地;消息发送给谁.
		Destination destination;
		// 消费者，消息接收者
		MessageConsumer consumer;
		connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		try {
			// 构造从工厂得到连接对象
			connection = connectionFactory.createConnection();
			// 启动
			connection.start();
			// 获取操作连接
			session = connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);
			// 获取session注意参数值xingbo.xu-queue是一个服务器的queue，须在在ActiveMq的console配置
			destination = session.createQueue("hbhk-1?consumer.exclusive=true");
			consumer = session.createConsumer(destination);
//			consumer.setMessageListener(new MessageListener() {
//				public void onMessage(Message message) {
//					if (null != message) {
//						TextMessage tmsg = (TextMessage) message;
//						try {
//							System.out.println(Thread.currentThread().getName()
//									+ " 收到消息" + tmsg.getText());
//						} catch (JMSException e) {
//							e.printStackTrace();
//						}
//					}
//					
//				}
//			});
			while (true) {
				// 设置接收者接收消息的时间，为了便于测试，这里谁定为100s
				TimeUnit.SECONDS.sleep(1);
				TextMessage message = (TextMessage) consumer.receive(1000);
				if (null != message) {
					System.out.println(Thread.currentThread().getName()
							+ " 收到消息" + message.getText());
				}
				// else {
				// break;
				// }
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != connection)
					connection.close();
			} catch (Throwable ignore) {
			}
		}

	}
}

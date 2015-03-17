package org.hbhk.test.ws.test;
import org.hbhk.test.webservice.IHelloWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class CxfSpringClientTest {
	public static final Logger log = LoggerFactory
			.getLogger(CxfSpringClientTest.class);
	public static void main(String[] args) {
		ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath:cxf-client.xml");
		
		IHelloWorld helloWorld = (IHelloWorld) classPathXmlApplicationContext.getBean("helloWorld");
		helloWorld.getOrder("name");
	}
}


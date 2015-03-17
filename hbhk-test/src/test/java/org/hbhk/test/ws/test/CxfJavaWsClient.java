package org.hbhk.test.ws.test;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.hbhk.test.webservice.IHelloWorld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CxfJavaWsClient {
	public static final Logger log = LoggerFactory
			.getLogger(CxfJavaWsClient.class);

	public static void main(String[] args) {
		// 调用WebService
		JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(IHelloWorld.class);
		factory.setAddress("http://localhost:8080/cxf/ws//HelloWorld");
		IHelloWorld service = (IHelloWorld) factory.create();
		service.getOrder("hbhk");
	}
}

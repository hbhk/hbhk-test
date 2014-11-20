package org.hbhk.test.webservice;

import junit.framework.TestCase;

import org.hbhk.test.webservice.impl.HelloWorldService;

public class TestEndpoint extends TestCase {

	public static void main(String[] args) {
		HelloWorldService service = new HelloWorldService();
		Order o = service.getHelloWorldPort().getOrder();

		System.out.println(o.getShop());

	}

}

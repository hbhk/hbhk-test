package org.hbhk.test.webservice;

import javax.jws.WebService;

import org.hbhk.test.webservice.info.Order;


@WebService
public interface IHelloWorld {
	
	Order getOrder();
}

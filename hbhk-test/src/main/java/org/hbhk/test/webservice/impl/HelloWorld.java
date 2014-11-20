package org.hbhk.test.webservice.impl;

import java.util.Date;

import javax.jws.WebService;

import org.hbhk.test.webservice.IHelloWorld;
import org.hbhk.test.webservice.info.Order;

@WebService
public class HelloWorld implements IHelloWorld {


	public Order getOrder() {
		if("a".equals("a")){
			throw new RuntimeException("aaaaaaaaaaaaaaaa");
		}
		Order o = new Order();
		o.setCreateTime(new Date());
		o.setDescription("description");
		o.setName("name");
		o.setSendTime(new Date());
		o.setShop("shop");
		o.setType(1);
		return o;
	}
}

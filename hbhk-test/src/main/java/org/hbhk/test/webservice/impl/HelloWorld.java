package org.hbhk.test.webservice.impl;

import java.util.List;

import javax.jws.WebService;

import org.hbhk.test.webservice.IHelloWorld;

@WebService
public class HelloWorld implements IHelloWorld {

	public String sayHi(String text) {
		
		System.out.println(text);
		return null;
	}

	public String sayHiToUser(String user) {
		return null;
	}

	public String[] SayHiToUserList(List<String> userList) {
		return null;
	}
}

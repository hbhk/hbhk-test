package org.hbhk.test.webservice;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;


@WebService
public interface IHelloWorld {

	String sayHi(@WebParam(name = "text") String text);

	String sayHiToUser(String user);

	String[] SayHiToUserList(List<String> userList);
}

package org.hbhk.test.ws.test;

import junit.framework.TestCase;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.hbhk.test.webservice.IHelloWorld;

public class TestEndpoint extends TestCase {
    
    private static final String ADDRESS = "http://localhost:9000/cxfdemo"; 
    protected void setUp() throws Exception {
        super.setUp();
        
       
    }
     
    public static void main(String[] args) {
//    	 System.out.println("Starting Server");  
//         Endpoint.publish(ADDRESS, demo);
//         System.out.println("Start success");
//         
//         testSayHello();
	}
    
    public static  void testSayHello(){
        
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(IHelloWorld.class);
        factory.setAddress(ADDRESS);
        IHelloWorld client = (IHelloWorld)factory.create();
        
    }
}


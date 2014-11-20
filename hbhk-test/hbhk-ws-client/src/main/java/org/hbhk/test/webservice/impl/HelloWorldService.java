
/*
 * 
 */

package org.hbhk.test.webservice.impl;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

import org.hbhk.test.webservice.IHelloWorld;

/**
 * This class was generated by Apache CXF 2.1.4
 * Thu Nov 20 15:02:50 CST 2014
 * Generated source version: 2.1.4
 * 
 */


@WebServiceClient(name = "HelloWorldService", 
                  wsdlLocation = "http://localhost:9000/cxfdemo?wsdl",
                  targetNamespace = "http://impl.webservice.test.hbhk.org/") 
public class HelloWorldService extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://impl.webservice.test.hbhk.org/", "HelloWorldService");
    public final static QName HelloWorldPort = new QName("http://impl.webservice.test.hbhk.org/", "HelloWorldPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:9000/cxfdemo?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from http://localhost:9000/cxfdemo?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public HelloWorldService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public HelloWorldService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public HelloWorldService() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns IHelloWorld
     */
    @WebEndpoint(name = "HelloWorldPort")
    public IHelloWorld getHelloWorldPort() {
        return super.getPort(HelloWorldPort, IHelloWorld.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns IHelloWorld
     */
    @WebEndpoint(name = "HelloWorldPort")
    public IHelloWorld getHelloWorldPort(WebServiceFeature... features) {
        return super.getPort(HelloWorldPort, IHelloWorld.class, features);
    }

}

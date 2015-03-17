package org.hbhk.test.webservice;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.hbhk.test.webservice.info.Order;


@WebService
public interface IHelloWorld {
	  /**
     * @WebResult(name="addResult")
     *     此注解可加可不加，如果加了但不指定name属性的值跟没加是一样的，
     *      加上name的效果就是在wsdl文件的定义中将该方法的返回值的名称固定了，
     *      而不是【方法名Response】,例如add方法的返回参数的定义将为：addResponse
     * 
     * @WebParam(name="a")
     *     此注解是将方法的参数的名称用一个有意义的名称进行定义,
     *     如果不定义那wsdl中将是arg0、arg1....这种无意义的名称
     * @param a
     * @param b
     * @return
     */
    @WebResult(name="getOrder")
	Order getOrder(@WebParam(name="name")String name);
}

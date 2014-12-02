package org.hbhk.test.i18n.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
* @Description: 需要国际化的类
* @author 何波
* @date 2014年11月19日 上午10:53:14 
*
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface I18n {
	/**
	 * 
	* @author 何波
	* @Description: 分类
	* @return   
	* String   
	* @throws
	 */
	String category() default "";
	
	/**
	 * 指定需要转化国际化列使用的长度 ,
	 * 国际化根据长度分为三张表0-255 256-1000  大于1000
	 */
	int length() default 255;
}

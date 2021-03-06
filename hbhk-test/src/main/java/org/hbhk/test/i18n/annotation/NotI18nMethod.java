package org.hbhk.test.i18n.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 
* @Description: 不需要国际化的方法 
* 如果查询对象为hibernate方式获取并执行修改操作务必加上该注解
* @author 何波
* @date 2014年11月19日 上午10:53:46 
*
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotI18nMethod {
	
}

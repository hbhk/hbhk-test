package org.hbhk.test.i18n.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
* @Description:表示分类数据中唯一标识
* @author 何波
* @date 2014年11月19日 上午10:51:36 
*
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface I18nKey {
}


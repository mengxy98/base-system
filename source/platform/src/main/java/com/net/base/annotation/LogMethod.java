package com.net.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:针对方法进行日志记录
 * @Company:东方网信股份有限公司
 * @Date: 2016年1月30日
 * @Time: 下午10:42:26
 * @author gongjzh@net-east.com
 */
@Target({ ElementType.FIELD,ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogMethod {

	String funcType() default "";
	
	String description() default "";
	
    boolean isIgnore() default false;

}

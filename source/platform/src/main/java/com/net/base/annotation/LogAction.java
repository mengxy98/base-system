package com.net.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:日志允许记录的方法，此注解仅针对基础框架中的action(create, delete,
 *                                                   update)方式访问的controller
 * @Company:东方网信股份有限公司
 * @Date: 2016年1月29日
 * @Time: 上午11:21:34
 * @author gongjzh@net-east.com
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAction {

	String funcType() default "";
	String description() default "";

}

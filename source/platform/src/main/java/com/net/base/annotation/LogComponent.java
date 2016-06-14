package com.net.base.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:操作日志注解
 * @Company:东方网信股份有限公司
 * @Date: 2016年1月28日
 * @Time: 下午6:20:19
 * @author gongjzh@net-east.com
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogComponent {

	String funcType() default "";
	String description() default "";

}

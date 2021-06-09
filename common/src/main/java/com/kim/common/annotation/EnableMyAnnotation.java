package com.kim.common.annotation;

import com.kim.common.config.MyConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author kim
 * @Since 2021/6/9
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import( MyConfig.class)
public @interface EnableMyAnnotation {
}

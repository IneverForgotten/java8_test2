package org.nfec.common.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义@Valid校验注解
 */
@Target({ElementType.FIELD,ElementType.PARAMETER})  //注解作用域
@Retention(RetentionPolicy.RUNTIME)  //注解作用时间
@Constraint(validatedBy = TimeStampValidConstraintValidator.class) //执行校验逻辑的类
public @interface TimeStampValid {

    //校验不过时候的信息
    String message() default "时间戳校验错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

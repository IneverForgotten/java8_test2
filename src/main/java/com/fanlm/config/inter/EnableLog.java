package com.fanlm.config.inter;


import com.fanlm.filter.LogFilter;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LogFilter.class)
public @interface EnableLog {

}

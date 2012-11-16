package org.kubek2k.springockito.annotations;

import org.springframework.test.context.support.AbstractGenericContextLoader;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LoadContextWith {
    Class<? extends AbstractGenericContextLoader> value();
}

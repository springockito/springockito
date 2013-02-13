package org.kubek2k.tools;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Ordered {

    public int FIRST = Integer.MIN_VALUE;
    public int LAST = Integer.MAX_VALUE;
    public int DEFAULT = 0;

    int value() default DEFAULT;

}

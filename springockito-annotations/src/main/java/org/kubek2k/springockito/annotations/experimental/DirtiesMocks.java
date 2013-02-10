package org.kubek2k.springockito.annotations.experimental;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface DirtiesMocks {

    static enum ClassMode {

        AFTER_CLASS,
        AFTER_EACH_TEST_METHOD
    }

    ClassMode classMode() default ClassMode.AFTER_CLASS;
}

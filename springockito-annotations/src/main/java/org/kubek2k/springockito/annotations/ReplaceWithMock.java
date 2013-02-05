package org.kubek2k.springockito.annotations;

import org.mockito.Answers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ReplaceWithMock {

    String name() default "";

    Answers defaultAnswer() default Answers.RETURNS_DEFAULTS;

    Class[] extraInterfaces() default {};

    BeanNameStrategy beanNameStrategy() default BeanNameStrategy.DEFAULT;

    String beanName() default "";

    public enum BeanNameStrategy {
        DEFAULT,
        FIELD_NAME,
        FIELD_TYPE_NAME
    }

}
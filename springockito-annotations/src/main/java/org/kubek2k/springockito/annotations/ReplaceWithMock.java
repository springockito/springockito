package org.kubek2k.springockito.annotations;

import org.mockito.Answers;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ReplaceWithMock {

    //mock settings

    String name() default "";

    Answers defaultAnswer() default Answers.RETURNS_DEFAULTS;

    Class[] extraInterfaces() default {};

    //bean naming

    BeanNameStrategy beanNameStrategy() default BeanNameStrategy.DEFAULT;

    String beanName() default "";

}
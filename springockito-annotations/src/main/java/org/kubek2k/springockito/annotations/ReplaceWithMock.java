package org.kubek2k.springockito.annotations;

import org.mockito.Answers;
import org.mockito.stubbing.Answer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ReplaceWithMock {
    Class [] extraInterfaces();

    Answers defaultAnswer();
}

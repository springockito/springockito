package org.kubek2k.springockito.annotations.internal.naming.strategies;

import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.WrapWithSpy;

import java.lang.reflect.Field;

public class ExplicitBeanNameNameResolver extends AbstractBeanNameResolver {

    @Override
    protected String resolveBeanName(Field field) {
        if (field.isAnnotationPresent(ReplaceWithMock.class)) {
            return field.getAnnotation(ReplaceWithMock.class).beanName();
        } else {
            return field.getAnnotation(WrapWithSpy.class).beanName();
        }
    }
}

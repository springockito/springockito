package org.kubek2k.springockito.annotations.internal.naming.strategies;

import org.kubek2k.springockito.annotations.ReplaceWithMock;

import java.lang.reflect.Field;

public class ExplicitBeanNameNameResolver extends AbstractBeanNameResolver {

    @Override
    protected String resolveBeanName(Field field) {
        return field.getAnnotation(ReplaceWithMock.class).beanName();
    }
}

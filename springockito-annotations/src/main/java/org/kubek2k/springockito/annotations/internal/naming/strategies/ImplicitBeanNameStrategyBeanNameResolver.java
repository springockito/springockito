package org.kubek2k.springockito.annotations.internal.naming.strategies;

import org.kubek2k.springockito.annotations.ReplaceWithMock;

import java.lang.reflect.Field;

import static org.kubek2k.springockito.annotations.ReplaceWithMock.BeanNameStrategy.DEFAULT;

public class ImplicitBeanNameStrategyBeanNameResolver extends AbstractBeanNameResolver {
    @Override
    protected String resolveBeanName(Field field) {
        ReplaceWithMock.BeanNameStrategy beanNameStrategy = field.getAnnotation(ReplaceWithMock.class).beanNameStrategy();
        if (beanNameStrategy == DEFAULT) {
            return field.getName();
        }
        return null;
    }
}

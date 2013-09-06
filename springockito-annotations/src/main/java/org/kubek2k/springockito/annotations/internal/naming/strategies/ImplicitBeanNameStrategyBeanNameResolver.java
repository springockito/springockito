package org.kubek2k.springockito.annotations.internal.naming.strategies;

import org.kubek2k.springockito.annotations.BeanNameStrategy;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.WrapWithSpy;

import java.lang.reflect.Field;


public class ImplicitBeanNameStrategyBeanNameResolver extends AbstractBeanNameResolver {
    @Override
    protected String resolveBeanName(Field field) {
        BeanNameStrategy beanNameStrategy;
        if (field.isAnnotationPresent(ReplaceWithMock.class)) {
            beanNameStrategy = field.getAnnotation(ReplaceWithMock.class).beanNameStrategy();
        } else {
            beanNameStrategy = field.getAnnotation(WrapWithSpy.class).beanNameStrategy();
        }
        if (beanNameStrategy == BeanNameStrategy.DEFAULT) {
            return field.getName();
        }
        return null;
    }
}

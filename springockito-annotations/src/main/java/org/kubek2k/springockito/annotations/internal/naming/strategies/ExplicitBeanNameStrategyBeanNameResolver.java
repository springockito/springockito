package org.kubek2k.springockito.annotations.internal.naming.strategies;

import org.kubek2k.springockito.annotations.BeanNameStrategy;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.WrapWithSpy;

import java.lang.reflect.Field;

import static org.kubek2k.springockito.annotations.BeanNameStrategy.FIELD_NAME;
import static org.kubek2k.springockito.annotations.BeanNameStrategy.FIELD_TYPE_NAME;

public class ExplicitBeanNameStrategyBeanNameResolver extends AbstractBeanNameResolver {

    @Override
    protected String resolveBeanName(Field field) {
        BeanNameStrategy beanNameStrategy;
        if (field.isAnnotationPresent(ReplaceWithMock.class)) {
            beanNameStrategy = field.getAnnotation(ReplaceWithMock.class).beanNameStrategy();
        } else {
            beanNameStrategy = field.getAnnotation(WrapWithSpy.class).beanNameStrategy();
        }

        if (beanNameStrategy == FIELD_NAME) {
            return field.getName();
        } else if (beanNameStrategy == FIELD_TYPE_NAME) {
            return field.getType().getName();
        }
        return null;
    }

}

package org.kubek2k.springockito.annotations.internal.naming.strategies;

import org.kubek2k.springockito.annotations.ReplaceWithMock;

import java.lang.reflect.Field;

import static org.kubek2k.springockito.annotations.ReplaceWithMock.BeanNameStrategy.FIELD_NAME;
import static org.kubek2k.springockito.annotations.ReplaceWithMock.BeanNameStrategy.FIELD_TYPE_NAME;

public class ExplicitBeanNameStrategyBeanNameResolver extends AbstractBeanNameResolver {

    @Override
    protected String resolveBeanName(Field field) {
        ReplaceWithMock.BeanNameStrategy beanNameStrategy = field.getAnnotation(ReplaceWithMock.class).beanNameStrategy();
        if (beanNameStrategy == FIELD_NAME) {
            return field.getName();
        } else if (beanNameStrategy == FIELD_TYPE_NAME) {
            return field.getType().getName();
        }
        return null;
    }

}

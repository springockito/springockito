package org.kubek2k.springockito.annotations.internal.naming.strategies;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.reflect.Field;

public class QualifierBeanNameResolver extends AbstractBeanNameResolver {

    @Override
    protected String resolveBeanName(Field field) {
        Qualifier annotation = field.getAnnotation(Qualifier.class);
        if (annotation == null) {
            return null;
        }
        return annotation.value();
    }
}

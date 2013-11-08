package org.kubek2k.springockito.annotations.internal.naming.strategies;

import org.kubek2k.springockito.annotations.internal.naming.BeanNameResolver;

import java.lang.reflect.Field;

public abstract class AbstractBeanNameResolver implements BeanNameResolver {

    protected abstract String resolveBeanName(Field field);

    public String retrieveBeanName(Field field) {
        return resolveBeanName(field);
    }

    public boolean canGetBeanName(Field field) {
        String beanName = resolveBeanName(field);
        return isNotBlank(beanName);
    }

    private boolean isNotBlank(String beanName) {
        return beanName != null && beanName.length() > 0;
    }
}

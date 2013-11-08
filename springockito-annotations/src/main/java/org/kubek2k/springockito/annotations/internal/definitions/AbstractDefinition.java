package org.kubek2k.springockito.annotations.internal.definitions;

public abstract class AbstractDefinition<T> implements SpringockitoDefinition {

    private String targetBeanName;

    protected abstract T getThis();

    public String getTargetBeanName() {
        return targetBeanName;
    }

    public T withTargetBeanName(String targetBeanName) {
        this.targetBeanName = targetBeanName;
        return getThis();
    }
}

package org.kubek2k.springockito.annotations.internal.definer;

import org.kubek2k.springockito.annotations.internal.definitions.SpringockitoDefinition;
import org.kubek2k.springockito.annotations.internal.definitions.bean.SpringockitoBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;

public abstract class Definer<T extends SpringockitoDefinition> {

    public SpringockitoBeanDefinition define(T springockitoDefinition) {
        return new SpringockitoBeanDefinition()
                .withSpringBeanDefinition(getSpringBeanDefinition(springockitoDefinition))
                .withSpringBeanName(getSpringBeanName(springockitoDefinition));
    }

    protected abstract String getSpringBeanName(T springockitoDefinition);

    protected abstract BeanDefinition getSpringBeanDefinition(T springockitoDefinition);
}

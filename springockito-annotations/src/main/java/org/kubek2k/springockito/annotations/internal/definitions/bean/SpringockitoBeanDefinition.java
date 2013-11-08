package org.kubek2k.springockito.annotations.internal.definitions.bean;

import org.springframework.beans.factory.config.BeanDefinition;

public class SpringockitoBeanDefinition {

    private String springBeanName;
    private BeanDefinition springBeanDefinition;

    public String getSpringBeanName() {
        return springBeanName;
    }

    public SpringockitoBeanDefinition withSpringBeanName(String springBeanName) {
        this.springBeanName = springBeanName;
        return this;
    }

    public BeanDefinition getSpringBeanDefinition() {
        return springBeanDefinition;
    }

    public SpringockitoBeanDefinition withSpringBeanDefinition(BeanDefinition springBeanDefinition) {
        this.springBeanDefinition = springBeanDefinition;
        return this;
    }
}

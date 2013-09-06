package org.kubek2k.springockito.annotations.internal;

import org.kubek2k.springockito.annotations.internal.definitions.SpringockitoDefinition;
import org.kubek2k.springockito.annotations.internal.definitions.bean.SpringockitoBeanDefinition;
import org.springframework.context.support.GenericApplicationContext;

public class Loader {

    private DefinitionRegistry definitionRegistry = new DefinitionRegistry();
    private SpringockitoDefinitionFinder springockitoDefinitionFinder = new SpringockitoDefinitionFinder();

    public void defineMocksAndSpies(Class<?> clazz) {
        this.definitionRegistry.registerAll(springockitoDefinitionFinder.findSpringockitoDefinitions(clazz));
    }

    public void registerMocksAndSpies(GenericApplicationContext context) {
        for (SpringockitoDefinition springockitoDefinition : definitionRegistry.getRegistered()) {
            SpringockitoBeanDefinition beanDefinition = springockitoDefinition.createSpringockitoBeanDefinition();
            context.registerBeanDefinition(beanDefinition.getSpringBeanName(), beanDefinition.getSpringBeanDefinition());
        }
    }
}

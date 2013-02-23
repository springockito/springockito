package org.kubek2k.springockito.annotations.internal.definer;

import org.kubek2k.springockito.annotations.internal.definitions.SpyDefinition;
import org.kubek2k.springockito.core.internal.spy.SpySpringockitoPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

public class SpyDefiner extends Definer<SpyDefinition> {

    public static final SpyDefiner INSTANCE = new SpyDefiner();

    private SpyDefiner(){}

    public static SpyDefiner getInstance(){
        return INSTANCE;
    }

    @Override
    protected String getSpringBeanName(SpyDefinition springockitoDefinition) {
        return springockitoDefinition.getTargetBeanName() + "$$POST_PROCESSOR_SPY";
    }

    @Override
    protected BeanDefinition getSpringBeanDefinition(SpyDefinition spyDefinition) {
        return BeanDefinitionBuilder
                .rootBeanDefinition(SpySpringockitoPostProcessor.class.getCanonicalName())
                .addPropertyValue("beanName", spyDefinition.getTargetBeanName())
                .getBeanDefinition();
    }
}

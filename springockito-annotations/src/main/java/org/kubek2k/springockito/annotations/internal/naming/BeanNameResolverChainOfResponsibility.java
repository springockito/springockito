package org.kubek2k.springockito.annotations.internal.naming;

import org.kubek2k.springockito.annotations.internal.naming.strategies.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BeanNameResolverChainOfResponsibility implements BeanNameResolver {

    private List<AbstractBeanNameResolver> resolversChain;

    public BeanNameResolverChainOfResponsibility() {
        this.resolversChain = new ArrayList<AbstractBeanNameResolver>();
        Collections.addAll(resolversChain,
                new ExplicitBeanNameNameResolver(),
                new ExplicitBeanNameStrategyBeanNameResolver(),
                new QualifierBeanNameResolver(),
                new ResourceBeanNameResolver(),
                new ImplicitBeanNameStrategyBeanNameResolver()
        );
    }

    public String retrieveBeanName(Field field) {
        for (AbstractBeanNameResolver resolver : resolversChain) {
            if (resolver.canGetBeanName(field)) {
                return resolver.retrieveBeanName(field);
            }
        }
        return getFallbackDefault(field);
    }

    private String getFallbackDefault(Field field) {
        return field.getName();
    }
}

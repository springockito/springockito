package org.kubek2k.springockito.annotations.internal.definitions;

import org.kubek2k.springockito.annotations.internal.definer.SpyDefiner;
import org.kubek2k.springockito.annotations.internal.definitions.bean.SpringockitoBeanDefinition;

public class SpyDefinition extends AbstractDefinition<SpyDefinition> {

    public SpringockitoBeanDefinition createSpringockitoBeanDefinition(){
        return SpyDefiner.getInstance().define(this);
    }

    @Override
    protected SpyDefinition getThis() {
        return this;
    }
}

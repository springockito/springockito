package org.kubek2k.springockito.annotations.internal.definitions;

import org.kubek2k.springockito.annotations.internal.definitions.bean.SpringockitoBeanDefinition;

public interface SpringockitoDefinition {

    String getTargetBeanName();

    SpringockitoBeanDefinition createSpringockitoBeanDefinition();
}

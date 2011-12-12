package org.kubek2k.springockito.annotations;

import org.kubek2k.springockito.annotations.factory.SpyFactoryBean;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

public class MockitoSpiesDefiner {

    AbstractBeanDefinition createSpyDefinition(String wrappedBeanName) {
        return BeanDefinitionBuilder
                .rootBeanDefinition(SpyFactoryBean.class.getCanonicalName())
                .addConstructorArgReference(wrappedBeanName)
                .getBeanDefinition();
    }

}

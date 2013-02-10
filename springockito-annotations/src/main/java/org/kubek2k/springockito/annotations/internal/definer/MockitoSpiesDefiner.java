package org.kubek2k.springockito.annotations.internal.definer;

import org.kubek2k.springockito.annotations.internal.factory.SpyFactoryBean;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

public class MockitoSpiesDefiner {

    public AbstractBeanDefinition createSpyDefinition(String wrappedBeanName) {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                .rootBeanDefinition(SpyFactoryBean.class.getCanonicalName())
                .addConstructorArgReference(wrappedBeanName)
                .getBeanDefinition();
        beanDefinition.setPrimary(true);
        return beanDefinition;
    }

}

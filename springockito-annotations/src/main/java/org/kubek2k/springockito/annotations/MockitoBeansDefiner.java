package org.kubek2k.springockito.annotations;

import org.kubek2k.mockito.spring.factory.MockFactoryBean;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

class MockitoBeansDefiner {
    public AbstractBeanDefinition createMockFactoryBeanDefinition(Class<?> mockClass) {
        return BeanDefinitionBuilder.genericBeanDefinition(MockFactoryBean.class.getCanonicalName())
                .addConstructorArgValue(mockClass)
                .getBeanDefinition();
    }
}

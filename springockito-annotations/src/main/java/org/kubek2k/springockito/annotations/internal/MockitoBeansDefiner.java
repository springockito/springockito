package org.kubek2k.springockito.annotations.internal;

import org.kubek2k.springockito.annotations.internal.factory.MockFactoryBean;
import org.mockito.Answers;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

public class MockitoBeansDefiner {
    public AbstractBeanDefinition createMockFactoryBeanDefinition(Class<?> mockClass, Class[] extraInterfaces, String mockName, Answers defaultAnswer) {
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(MockFactoryBean.class.getCanonicalName())
                .addConstructorArgValue(mockClass)
                .addConstructorArgValue(extraInterfaces)
                .addConstructorArgValue(mockName)
                .addConstructorArgValue(defaultAnswer)
                .getBeanDefinition();
        beanDefinition.setPrimary(true);
        return beanDefinition;
    }
}

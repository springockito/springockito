package org.kubek2k.springockito.annotations;

import org.kubek2k.springockito.annotations.factory.MockFactoryBean;
import org.mockito.Answers;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

class MockitoBeansDefiner {
    public AbstractBeanDefinition createMockFactoryBeanDefinition(Class<?> mockClass, Class[] extraInterfaces, String mockName, Answers defaultAnswer) {
        return BeanDefinitionBuilder.genericBeanDefinition(MockFactoryBean.class.getCanonicalName())
                .addConstructorArgValue(mockClass)
                .addConstructorArgValue(extraInterfaces)
                .addConstructorArgValue(mockName)
                .addConstructorArgValue(defaultAnswer)
                .getBeanDefinition();
    }
}

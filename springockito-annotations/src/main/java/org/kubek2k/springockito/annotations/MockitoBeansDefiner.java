package org.kubek2k.springockito.annotations;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

class MockitoBeansDefiner {
    AbstractBeanDefinition createMockDefinition(Class<?> mockClass) {
        return BeanDefinitionBuilder.genericBeanDefinition("org.mockito.Mockito")
                .setFactoryMethod("mock")
                .addConstructorArgValue(mockClass.getCanonicalName())
                .getBeanDefinition();
    }
}

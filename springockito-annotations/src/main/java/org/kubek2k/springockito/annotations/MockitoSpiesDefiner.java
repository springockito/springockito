package org.kubek2k.springockito.annotations;

import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;

public class MockitoSpiesDefiner {

    AbstractBeanDefinition createSpyDefinition(String wrappedBeanName) {
        return BeanDefinitionBuilder
                .rootBeanDefinition("org.mockito.Mockito", "spy")
                .addConstructorArgReference(wrappedBeanName)
                .getBeanDefinition();
    }

}

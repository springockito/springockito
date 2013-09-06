  package org.kubek2k.mockito.spring;

import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MockitoSpyBeanPostProcessor implements BeanPostProcessor {

    private String beanName;

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (this.beanName.equals(beanName)) {
            return Mockito.spy(bean);
        } else {
            return bean;
        }
    }
    public void setBeanName(String matchingName) {
        this.beanName = matchingName;
    }

}

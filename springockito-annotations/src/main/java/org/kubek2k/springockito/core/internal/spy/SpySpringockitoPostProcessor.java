package org.kubek2k.springockito.core.internal.spy;

import org.kubek2k.springockito.core.internal.ResettableSpringockito;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class SpySpringockitoPostProcessor implements BeanPostProcessor,ResettableSpringockito {

    private String beanName;
    private Object spy;

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (this.beanName.equals(beanName)) {
            spy = Mockito.spy(bean);
            return spy;
        } else {
            return bean;
        }
    }

    public void setBeanName(String matchingName) {
        this.beanName = matchingName;
    }

    public void reset() {
        try {
            Mockito.reset(spy);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

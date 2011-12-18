package org.kubek2k.mockito.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

public class MockitoSpyBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected String getBeanClassName(Element element) {
        return "org.kubek2k.mockito.spring.MockitoSpyBeanPostProcessor";
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder beanBuilder) {
        beanBuilder.addPropertyValue("beanName", element.getAttribute("beanName"));
    }
    
    @Override
    protected boolean shouldGenerateIdAsFallback() {
        return true;
    }
}

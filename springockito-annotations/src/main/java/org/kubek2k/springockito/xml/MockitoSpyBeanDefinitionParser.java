package org.kubek2k.springockito.xml;

import org.kubek2k.springockito.core.internal.spy.SpySpringockitoPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

public class MockitoSpyBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    @Override
    protected String getBeanClassName(Element element) {
        return SpySpringockitoPostProcessor.class.getCanonicalName();
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

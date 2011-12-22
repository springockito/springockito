package org.kubek2k.mockito.spring;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

public class MockitoMockBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    
    @Override
    protected String getBeanClassName(Element element) {
        return "org.kubek2k.mockito.spring.factory.MockFactoryBean";
    }
    
    @Override
    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        bean.addConstructorArgValue(element.getAttribute("class"));
    }
}

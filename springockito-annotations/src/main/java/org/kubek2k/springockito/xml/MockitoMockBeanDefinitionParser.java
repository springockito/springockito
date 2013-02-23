package org.kubek2k.springockito.xml;

import org.kubek2k.springockito.core.internal.mock.SynchronizedMockFactorySpringockito;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

import static java.lang.Boolean.valueOf;

public class MockitoMockBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {
    
    @Override
    protected String getBeanClassName(Element element) {
        return SynchronizedMockFactorySpringockito.class.getCanonicalName();
    }
    
    @Override
    protected void doParse(Element element, BeanDefinitionBuilder builder) {
        String threadSafe = element.getAttribute("threadSafe");
        builder.addPropertyValue("mockClass",element.getAttribute("class"))
                .addPropertyValue("threadSafe", valueOf(threadSafe));
    }

    @Override
    protected void postProcessComponentDefinition(BeanComponentDefinition componentDefinition) {
        super.postProcessComponentDefinition(componentDefinition);
        componentDefinition.getBeanDefinition().setPrimary(true);
    }
}

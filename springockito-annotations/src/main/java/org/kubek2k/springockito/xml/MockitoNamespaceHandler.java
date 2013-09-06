package org.kubek2k.springockito.xml;

import org.springframework.beans.factory.xml.NamespaceHandler;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MockitoNamespaceHandler extends NamespaceHandlerSupport implements NamespaceHandler {

    public void init() {
        registerBeanDefinitionParser("mock", new MockitoMockBeanDefinitionParser());
        registerBeanDefinitionParser("spy", new MockitoSpyBeanDefinitionParser());
    }
}

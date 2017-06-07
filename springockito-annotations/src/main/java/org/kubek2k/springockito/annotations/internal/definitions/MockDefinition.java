package org.kubek2k.springockito.annotations.internal.definitions;

import org.kubek2k.springockito.annotations.internal.MockitoMockSettings;

import java.lang.reflect.Method;

import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.internal.definer.MockDefiner;
import org.kubek2k.springockito.annotations.internal.definitions.bean.SpringockitoBeanDefinition;

public class MockDefinition extends AbstractDefinition<MockDefinition>{

    private ReplaceWithMock annotationInstance;
    private Class<?> mockClass;
    private Method mockBehavior;

    public Class<?> getMockClass() {
        return mockClass;
    }

    public MockitoMockSettings getMockitoMockSettings() {
        return new MockitoMockSettings()
                .withMockName(annotationInstance.name())
                .withDefaultAnswer(annotationInstance.defaultAnswer())
                .withExtraInterfaces(annotationInstance.extraInterfaces())
                .withMockBehavior(mockBehavior);
    }

    public MockDefinition withAnnotationInstance(ReplaceWithMock annotationInstance) {
        this.annotationInstance = annotationInstance;
        return this;
    }

    public MockDefinition withMockClass(Class<?> mockClass) {
        this.mockClass = mockClass;
        return this;
    }
    
    public MockDefinition withMockBehavior(Method mockBehavior) {
        this.mockBehavior = mockBehavior;
        return this;
    }

    public SpringockitoBeanDefinition createSpringockitoBeanDefinition() {
        return MockDefiner.getInstance().define(this);
    }

    @Override
    protected MockDefinition getThis() {
        return this;
    }
}

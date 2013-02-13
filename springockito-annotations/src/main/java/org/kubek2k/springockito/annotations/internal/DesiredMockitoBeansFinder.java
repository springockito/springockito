package org.kubek2k.springockito.annotations.internal;

import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.WrapWithSpy;
import org.kubek2k.springockito.annotations.internal.naming.BeanNameResolver;
import org.kubek2k.springockito.annotations.internal.naming.BeanNameResolverChainOfResponsibility;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class DesiredMockitoBeansFinder {

    private final BeanNameResolver beanNameResolver = new BeanNameResolverChainOfResponsibility();

    public static class MockProperties<T extends Annotation> {

        private T annotationInstance;
        private Class<?> mockClass;

        public MockProperties(T annotationInstance, Class<?> mockClass) {
            this.annotationInstance = annotationInstance;
            this.mockClass = mockClass;
        }

        public T getAnnotationInstance() {
            return annotationInstance;
        }

        public Class<?> getMockClass() {
            return mockClass;
        }

    }

    public Map<String, MockProperties<ReplaceWithMock>> findMockedBeans(Class<?> clazz) {
        Map<String, MockProperties<ReplaceWithMock>> mockedBeans = new LinkedHashMap<String, MockProperties<ReplaceWithMock>>();
        for (Field field : clazz.getDeclaredFields()) {
            ReplaceWithMock annotationInstance = field.getAnnotation(ReplaceWithMock.class);
            if (annotationInstance != null) {
                String beanName = getBeanName(field);
                MockProperties<ReplaceWithMock> mockProperties = new MockProperties<ReplaceWithMock>(annotationInstance, field.getType());
                mockedBeans.put(beanName, mockProperties);
            }
        }
        return mockedBeans;
    }

    public Set<String> findSpiedBeans(Class<?> clazz) {
        Set<String> mockedBeans = new HashSet<String>();
        for (Field field : clazz.getDeclaredFields()) {
            Annotation replaceWithMockAnnotation = field.getAnnotation(WrapWithSpy.class);
            if (replaceWithMockAnnotation != null) {
                String beanName = getBeanName(field);
                mockedBeans.add(beanName);
            }
        }
        return mockedBeans;
    }

    private String getBeanName(Field field) {
        return beanNameResolver.retrieveBeanName(field);
    }
}

package org.kubek2k.springockito.annotations;

import sun.reflect.annotation.AnnotationType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class DesiredMockitoBeansFinder {
    public static class MockProperties<AnnotationType extends Annotation> {
        private AnnotationType annotationValues;

        private Class<?> mockClass;

        public MockProperties(AnnotationType annotationValues, Class<?> mockClass) {
            this.annotationValues = annotationValues;
            this.mockClass = mockClass;
        }

        public AnnotationType getAnnotationValues() {
            return annotationValues;
        }

        public Class<?> getMockClass() {
            return mockClass;
        }
    }

    public Map<String, MockProperties<ReplaceWithMock>> findMockedBeans(Class<?> clazz) {
        return findAnnotatedFieldsTypes(clazz.getDeclaredFields(), ReplaceWithMock.class);
    }

    public Set<String> findSpiedBeans(Class<?> clazz) {
        return findAnnotatedFieldsTypes(clazz.getDeclaredFields(), WrapWithSpy.class).keySet();
    }

    private <AnnotationType extends Annotation> Map<String, MockProperties<AnnotationType>> findAnnotatedFieldsTypes(Field[] fieldsToScan, Class<AnnotationType> annotationClass) {
        Map<String, MockProperties<AnnotationType>> mockedBeans = new HashMap<String, MockProperties<AnnotationType>>();
        for (Field field : fieldsToScan) {
            Annotation replaceWithMockAnnotation = field.getAnnotation(annotationClass);
            if (replaceWithMockAnnotation != null) {
                mockedBeans.put(field.getName(), new MockProperties<AnnotationType>((AnnotationType)field.getAnnotation(annotationClass), field.getType()));
            }
        }
        return mockedBeans;
    }
}

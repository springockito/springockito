package org.kubek2k.springockito.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

class DesiredMockitoBeansFinder {
    public static class MockProperties<AnnotationType extends Annotation> {
        private AnnotationType annotationInstance;

        private Class<?> mockClass;
        private AnnotationClasspathRepresentationCreator<AnnotationType> annotationClasspathRepresentationCreator;

        public MockProperties(AnnotationType annotationInstance, Class<?> mockClass, AnnotationClasspathRepresentationCreator<AnnotationType> annotationClasspathRepresentationCreator) {
            this.annotationInstance = annotationInstance;
            this.mockClass = mockClass;
            this.annotationClasspathRepresentationCreator = annotationClasspathRepresentationCreator;
        }

        public AnnotationType getAnnotationInstance() {
            return annotationInstance;
        }

        public Class<?> getMockClass() {
            return mockClass;
        }

        public String getClasspathRepresentation() {
            return mockClass + ":" + annotationClasspathRepresentationCreator.toClasspathRepresentation(annotationInstance);
        }

    }

    private interface AnnotationClasspathRepresentationCreator<AnnotationType extends Annotation> {

        public String toClasspathRepresentation(AnnotationType annotationType);

    }

    public Map<String, MockProperties<ReplaceWithMock>> findMockedBeans(Class<?> clazz) {
        return findAnnotatedFieldsTypes(clazz.getDeclaredFields(), ReplaceWithMock.class, new AnnotationClasspathRepresentationCreator<ReplaceWithMock>() {
            public String toClasspathRepresentation(ReplaceWithMock replaceWithMock) {
                String defaultAnswer = replaceWithMock.defaultAnswer() != null ? replaceWithMock.defaultAnswer().toString() : "";
                String extraInterfaces = replaceWithMock.extraInterfaces() != null ? Arrays.toString(replaceWithMock.extraInterfaces()) : "";
                return replaceWithMock.name() + "," + defaultAnswer + "," + extraInterfaces;
            }
        });
    }

    public Set<String> findSpiedBeans(Class<?> clazz) {
        return findAnnotatedFieldsTypes(clazz.getDeclaredFields(), WrapWithSpy.class, new AnnotationClasspathRepresentationCreator<WrapWithSpy>() {
            public String toClasspathRepresentation(WrapWithSpy wrapWithSpy) {
                return "";
            }
        }).keySet();
    }

    private <AnnotationType extends Annotation> Map<String, MockProperties<AnnotationType>> findAnnotatedFieldsTypes(Field[] fieldsToScan, Class<AnnotationType> annotationClass, AnnotationClasspathRepresentationCreator<AnnotationType> annotationClasspathRepresentationCreator) {
        Map<String, MockProperties<AnnotationType>> mockedBeans = new LinkedHashMap<String, MockProperties<AnnotationType>>();
        for (Field field : fieldsToScan) {
            Annotation replaceWithMockAnnotation = field.getAnnotation(annotationClass);
            if (replaceWithMockAnnotation != null) {
                mockedBeans.put(field.getName(), new MockProperties<AnnotationType>((AnnotationType)field.getAnnotation(annotationClass), field.getType(), annotationClasspathRepresentationCreator));
            }
        }
        return mockedBeans;
    }

}

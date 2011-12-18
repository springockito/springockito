package org.kubek2k.springockito.annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class DesiredMockitoBeansFinder {
    public Map<String, Class<?>> findMockedBeans(Class<?> clazz) {
        return findAnnotatedFieldsTypes(clazz.getDeclaredFields(), ReplaceWithMock.class);
    }

    public Set<String> findSpiedBeans(Class<?> clazz) {
        return findAnnotatedFieldsTypes(clazz.getDeclaredFields(), WrapWithSpy.class).keySet();
    }

    private Map<String, Class<?>> findAnnotatedFieldsTypes(Field[] fieldsToScan, Class<? extends Annotation> annotationClass) {
        Map<String, Class<?>> mockedBeans = new HashMap<String, Class<?>>();
        for (Field field : fieldsToScan) {
            if (field.getAnnotation(annotationClass) != null) {
                mockedBeans.put(field.getName(), field.getType());
            }
        }
        return mockedBeans;
    }
}

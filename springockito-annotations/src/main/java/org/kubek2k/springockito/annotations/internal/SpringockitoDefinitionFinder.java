package org.kubek2k.springockito.annotations.internal;

import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.WrapWithSpy;
import org.kubek2k.springockito.annotations.internal.definitions.MockDefinition;
import org.kubek2k.springockito.annotations.internal.definitions.SpringockitoDefinition;
import org.kubek2k.springockito.annotations.internal.definitions.SpyDefinition;
import org.kubek2k.springockito.annotations.internal.naming.BeanNameResolver;
import org.kubek2k.springockito.annotations.internal.naming.BeanNameResolverChainOfResponsibility;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class SpringockitoDefinitionFinder {

    private BeanNameResolver beanNameResolver = new BeanNameResolverChainOfResponsibility();

    public Set<SpringockitoDefinition> findSpringockitoDefinitions(Class<?> clazz) {

        Set<SpringockitoDefinition> result = new HashSet<SpringockitoDefinition>();

        while (clazz != null) {
            result.addAll(findDefinitions(clazz));
            clazz = clazz.getSuperclass();
        }

        return result;
    }

    private Set<SpringockitoDefinition> findDefinitions(Class<?> currentClass) {

        Set<SpringockitoDefinition> definitions = new HashSet<SpringockitoDefinition>();

        for (Field field : currentClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(ReplaceWithMock.class)) {
                definitions.add(newMockDefinition(field));
            } else if (field.isAnnotationPresent(WrapWithSpy.class)) {
                definitions.add(newSpyDefinition(field));
            }
        }

        return definitions;
    }

    private SpyDefinition newSpyDefinition(Field field) {
        return new SpyDefinition()
                .withTargetBeanName(resolveBeanName(field));
    }

    private MockDefinition newMockDefinition(Field field) {
        return new MockDefinition()
                .withAnnotationInstance(field.getAnnotation(ReplaceWithMock.class))
                .withMockClass(field.getType())
                .withTargetBeanName(resolveBeanName(field));
    }

    private String resolveBeanName(Field field) {
        return beanNameResolver.retrieveBeanName(field);
    }
}

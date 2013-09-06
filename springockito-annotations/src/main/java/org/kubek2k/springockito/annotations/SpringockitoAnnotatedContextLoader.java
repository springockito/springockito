package org.kubek2k.springockito.annotations;

import org.kubek2k.springockito.annotations.internal.Loader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

public class SpringockitoAnnotatedContextLoader extends AnnotationConfigContextLoader{

    private Loader loader = new Loader();

    @Override
    protected void customizeContext(GenericApplicationContext context) {
        super.customizeContext(context);
        loader.registerMocksAndSpies(context);
    }

    @Override
    protected Class<?>[] detectDefaultConfigurationClasses(Class<?> declaringClass) {
        Class<?>[] clazz = super.detectDefaultConfigurationClasses(declaringClass);
        loader.defineMocksAndSpies(declaringClass);
        return clazz;
    }

    @Override
    public void processContextConfiguration(ContextConfigurationAttributes configAttributes) {
        super.processContextConfiguration(configAttributes);
        loader.defineMocksAndSpies(configAttributes.getDeclaringClass());
    }
}
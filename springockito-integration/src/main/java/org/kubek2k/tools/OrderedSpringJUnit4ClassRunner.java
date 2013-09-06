package org.kubek2k.tools;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrderedSpringJUnit4ClassRunner extends org.springframework.test.context.junit4.SpringJUnit4ClassRunner {

    public OrderedSpringJUnit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    @Override
    protected List<FrameworkMethod> computeTestMethods() {
        List<FrameworkMethod> frameworkMethods = super.computeTestMethods();
        Collections.sort(frameworkMethods, new Comparator<FrameworkMethod>() {
            @Override
            public int compare(FrameworkMethod o1, FrameworkMethod o2) {
                Integer i1 = getOrder(o1);
                Integer i2 = getOrder(o2);
                return i1.compareTo(i2);
            }
        });
        return frameworkMethods;
    }

    private int getOrder(FrameworkMethod frameworkMethod) {
        Method method = frameworkMethod.getMethod();
        if (method.isAnnotationPresent(Ordered.class)) {
            return method.getAnnotation(Ordered.class).value();
        } else {
            return Ordered.DEFAULT;
        }
    }
}

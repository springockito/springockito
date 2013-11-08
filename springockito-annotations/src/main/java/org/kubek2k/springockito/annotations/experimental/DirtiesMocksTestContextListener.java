package org.kubek2k.springockito.annotations.experimental;

import org.kubek2k.springockito.core.internal.ResettableSpringockito;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.lang.reflect.Method;
import java.util.Map;

public class DirtiesMocksTestContextListener extends AbstractTestExecutionListener {

    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        Method testMethod = testContext.getTestMethod();
        Class<?> testClass = testContext.getTestClass();
        if (testMethod.isAnnotationPresent(DirtiesMocks.class) || afterEveryMethodModeSet(testClass)) {
            resetMocks(testContext);
        }
    }

    private boolean afterEveryMethodModeSet(Class<?> testClass) {
        return testClass.isAnnotationPresent(DirtiesMocks.class)
                && testClass.getAnnotation(DirtiesMocks.class).classMode() == DirtiesMocks.ClassMode.AFTER_EACH_TEST_METHOD;
    }

    @Override
    public void afterTestClass(TestContext testContext) throws Exception {
        Class<?> testClass = testContext.getTestClass();
        if (afterClassModeSet(testClass)) {
            resetMocks(testContext);
        }
    }

    private boolean afterClassModeSet(Class<?> testClass) {
        return testClass.isAnnotationPresent(DirtiesMocks.class) && testClass.getAnnotation(DirtiesMocks.class).classMode() == DirtiesMocks.ClassMode.AFTER_CLASS;
    }

    private void resetMocks(TestContext testContext) {
        ApplicationContext applicationContext = testContext.getApplicationContext();
        Map<String, ResettableSpringockito> beansOfType = applicationContext.getBeansOfType(ResettableSpringockito.class);
        for (ResettableSpringockito resettableSpringockito : beansOfType.values()) {
            resettableSpringockito.reset();
        }
    }
}

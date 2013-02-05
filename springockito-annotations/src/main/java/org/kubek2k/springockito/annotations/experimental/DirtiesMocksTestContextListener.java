package org.kubek2k.springockito.annotations.experimental;

import org.kubek2k.springockito.annotations.internal.ResettableMock;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import java.lang.reflect.Method;
import java.util.Map;

public class DirtiesMocksTestContextListener extends AbstractTestExecutionListener {
    @Override
    public void afterTestMethod(TestContext testContext) throws Exception {
        Method testMethod = testContext.getTestMethod();
        if (testMethod.isAnnotationPresent(DirtiesMocks.class)) {
            ApplicationContext applicationContext = testContext.getApplicationContext();
            Map<String, ResettableMock> beansOfType = applicationContext.getBeansOfType(ResettableMock.class);
            for (ResettableMock resettableMock : beansOfType.values()) {
                resettableMock.resetMock();
            }
        }
    }
}

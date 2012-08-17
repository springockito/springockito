package org.kubek2k.mockito.spring.factory;

import org.mockito.Answers;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.mockito.internal.creation.MockSettingsImpl;
import org.mockito.internal.creation.jmock.ClassImposterizer;
import org.springframework.beans.factory.FactoryBean;


public class MockFactoryBean<T> implements FactoryBean<T> {

    private Class<T> mockClass;
    private final Class[] extraInterfaces;
    private final String mockName;
    private final Answers defaultAnswer;
    private T instance;

    public MockFactoryBean(Class<T> mockClass, Class[] extraInterfaces, String mockName, Answers defaultAnswer) {
        this.mockClass = mockClass;
        this.extraInterfaces = extraInterfaces;
        this.mockName = mockName;
        this.defaultAnswer = defaultAnswer;
    }

    public Class<? extends T> getObjectType() {
        return mockClass;
    }

    public boolean isSingleton() {
        return true;
    }

    public T getObject() throws Exception {
        if (instance == null) {
            instance = ClassImposterizer.INSTANCE.imposterise(new ThreadLocalMockMethodInterceptor<T>(mockClass),
                    mockClass, new Class[0]);
        }
        return instance;
    }

    protected T createInstance(Class<T> mockClass) {
        MockSettings settings = new MockSettingsImpl().extraInterfaces(extraInterfaces).defaultAnswer(
                defaultAnswer.get()).name(mockName);
        return Mockito.mock(mockClass, settings);
    }

}
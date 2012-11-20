package org.kubek2k.mockito.spring.factory;

import org.mockito.Mockito;
import org.mockito.internal.creation.jmock.ClassImposterizer;
import org.springframework.beans.factory.FactoryBean;


public class MockFactoryBean<T> implements FactoryBean<T> {

    private Class<T> mockClass;
    private T instance;
    
    public MockFactoryBean(Class<T> mockClass) {
        this.mockClass = mockClass;
    }

    public Class<? extends T> getObjectType() {
        return mockClass;
    }

    public boolean isSingleton() {
        return true;
    }

    public T getObject() throws Exception {
        if (instance == null) {
            instance = ClassImposterizer.INSTANCE.imposterise(new ThreadLocalMockMethodInterceptor<T>(mockClass), mockClass, new Class[0]);
        }
        return instance;
    }

    protected T createInstance(Class<T> mockClass) {
        return Mockito.mock(mockClass);
    }

}
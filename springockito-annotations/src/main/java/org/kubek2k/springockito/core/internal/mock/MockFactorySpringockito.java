package org.kubek2k.springockito.core.internal.mock;

import org.kubek2k.springockito.annotations.internal.MockitoMockSettings;
import org.kubek2k.springockito.core.internal.ResettableSpringockito;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.springframework.beans.factory.FactoryBean;


public class MockFactorySpringockito<T> implements FactoryBean<T>, ResettableSpringockito {

    protected Class<T> mockClass;
    protected MockitoMockSettings mockitoMockSettings;
    protected T instance;

    public Class<? extends T> getObjectType() {
        return mockClass;
    }

    public boolean isSingleton() {
        return true;
    }

    public T getObject() throws Exception {
        if (instance == null) {
            instance = Mockito.mock(mockClass, getMockSettings());
        }
        return instance;
    }

    private MockSettings getMockSettings() {
        if(mockitoMockSettings == null){
            mockitoMockSettings = MockitoMockSettings.DEFAULT;
        }
        return mockitoMockSettings.getMockSettings();
    }

    public void setMockClass(Class<T> mockClass) {
        this.mockClass = mockClass;
    }

    public void setMockitoMockSettings(MockitoMockSettings mockitoMockSettings) {
        this.mockitoMockSettings = mockitoMockSettings;
    }

    public void reset() {
        try {
            Mockito.<Object>reset(getObject());
        } catch (Exception e) {
            //TODO: throw runtime?
            e.printStackTrace();
        }
    }
}
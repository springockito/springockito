package org.kubek2k.mockito.spring.factory;

import org.mockito.Mockito;
import org.springframework.beans.factory.FactoryBean;

import java.util.Map;
import java.util.HashMap;

public class MockFactoryBean<T> implements FactoryBean<T> {

    private Class<T> mockClass;
    private String beanId;
    private boolean useStaticMap;

    private T thisInstance;

    private static Map<String, Object> objectMap = new HashMap<String, Object>();

    public MockFactoryBean(Class<T> mockClass, String beanId, boolean useStaticMap) {
        this.mockClass = mockClass;
        this.beanId = beanId;
        this.useStaticMap = useStaticMap;
    }

    public Class<? extends T> getObjectType() {
        return mockClass;
    }

    public boolean isSingleton() {
        return true;
    }

    public T getObject() throws Exception {
        if (useStaticMap) {
            T instance = (T) objectMap.get(beanId);

            if (instance == null) {
                instance = Mockito.mock(mockClass);
                objectMap.put(beanId, instance);
            }

            return instance;
            
        } else {
            if (thisInstance == null) {
                thisInstance = Mockito.mock(mockClass);
            }
            return thisInstance;
        }
    }

}
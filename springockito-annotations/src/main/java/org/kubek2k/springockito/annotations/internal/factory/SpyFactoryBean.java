package org.kubek2k.springockito.annotations.internal.factory;

import org.kubek2k.springockito.annotations.internal.ResettableMock;
import org.mockito.Mockito;
import org.springframework.beans.factory.FactoryBean;

public class SpyFactoryBean<T> implements FactoryBean<T>, ResettableMock {

    private T wrappedInstance;

    private T spyInstance;

    public SpyFactoryBean(T wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    public T getObject() throws Exception {
        if (spyInstance == null) {
            spyInstance = Mockito.spy(wrappedInstance);
        }
        return spyInstance;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends T> getObjectType() {
        return (Class<? extends T>) wrappedInstance.getClass();
    }

    public boolean isSingleton() {
        return true;
    }

    public void resetMock() {
        T object = null;
        try {
            object = getObject();
            Mockito.reset(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

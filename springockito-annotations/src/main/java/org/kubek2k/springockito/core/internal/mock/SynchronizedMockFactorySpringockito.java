package org.kubek2k.springockito.core.internal.mock;

import org.kubek2k.springockito.annotations.internal.MockitoMockSettings;
import org.mockito.internal.InvocationNotifierHandler;
import org.mockito.internal.MockHandler;
import org.mockito.internal.creation.MockSettingsImpl;
import org.mockito.internal.creation.jmock.ClassImposterizer;

public class SynchronizedMockFactorySpringockito<T> extends MockFactorySpringockito<T> {

    private boolean threadSafe;

    public T getObject() throws Exception {
        if (instance == null) {
            if(threadSafe){
                instance = getMockWithSynchronizedMethodIntercepting();
            } else {
                return super.getObject();
            }
        }
        return instance;
    }

    private T getMockWithSynchronizedMethodIntercepting() {
        SynchronizedMockMethodInterceptorFilter synchronizedMethodInterceptorFilter = createSynchronizedMethodInterceptorFilter();
        return ClassImposterizer.INSTANCE.imposterise(synchronizedMethodInterceptorFilter, mockClass, new Class[0]);
    }

    /**
     * Inspired by Mockito internals
     */
    private SynchronizedMockMethodInterceptorFilter createSynchronizedMethodInterceptorFilter() {
        MockSettingsImpl mockSettings = (MockSettingsImpl)MockitoMockSettings.DEFAULT.getMockSettings();
        MockHandler<T> mockHandler = new MockHandler<T>(mockSettings);
        InvocationNotifierHandler<T> invocationNotifierHandler = new InvocationNotifierHandler<T>(mockHandler, mockSettings);
        return new SynchronizedMockMethodInterceptorFilter(invocationNotifierHandler, mockSettings);
    }

    public void setThreadSafe(boolean threadSafe) {
        this.threadSafe = threadSafe;
    }
}

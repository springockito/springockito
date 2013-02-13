package org.kubek2k.mockito.spring.factory;

import java.lang.reflect.Method;

import org.mockito.Mockito;
import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;

public class ThreadLocalMockMethodInterceptor<T> implements MethodInterceptor {
    
    private InheritableThreadLocal<T> mockLocalForCurrentThread;
    private Class<T> mockClass;
    
    public ThreadLocalMockMethodInterceptor(Class<T> mockClass) {
        this.mockClass = mockClass;
        mockLocalForCurrentThread = new InheritableThreadLocal<T>();
    }

    private T getOrCreateMockForCurrentThread() {
        T threadLocalInstance = mockLocalForCurrentThread.get();
        if (threadLocalInstance == null) {
            threadLocalInstance = createMock();
            mockLocalForCurrentThread.set(threadLocalInstance);
        }
        return threadLocalInstance;
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        return method.invoke(getOrCreateMockForCurrentThread(), args);
    }


    private T createMock() {
        return Mockito.mock(mockClass);
    }
}

package org.kubek2k.mockito.spring.factory;

import java.lang.reflect.Method;

import org.mockito.Mockito;
import org.mockito.cglib.proxy.MethodInterceptor;
import org.mockito.cglib.proxy.MethodProxy;
import org.mockito.internal.MockitoInvocationHandler;
import org.mockito.internal.creation.MethodInterceptorFilter;
import org.mockito.internal.util.MockUtil;

public class ThreadLocalMockMethodInterceptor<T> extends MethodInterceptorFilter implements MethodInterceptor {

    private static final long serialVersionUID = -531647342545593891L;
    private InheritableThreadLocal<T> mockLocalForCurrentThread;
    private Class<T> mockClass;
    private static final MockUtil MOCK_UTIL = new MockUtil();
    
    public ThreadLocalMockMethodInterceptor(Class<T> mockClass) {
        super(null, null);
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
    
    @Override
    public MockitoInvocationHandler getHandler() {
        return (MockitoInvocationHandler) MOCK_UTIL.getMockHandler(getOrCreateMockForCurrentThread());
    }
}

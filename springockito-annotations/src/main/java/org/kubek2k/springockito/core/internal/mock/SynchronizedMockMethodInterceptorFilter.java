package org.kubek2k.springockito.core.internal.mock;

import org.mockito.cglib.proxy.MethodProxy;
import org.mockito.internal.MockitoInvocationHandler;
import org.mockito.internal.creation.MethodInterceptorFilter;
import org.mockito.internal.creation.MockSettingsImpl;

import java.lang.reflect.Method;

public class SynchronizedMockMethodInterceptorFilter extends MethodInterceptorFilter {

    public SynchronizedMockMethodInterceptorFilter(MockitoInvocationHandler handler, MockSettingsImpl mockSettings) {
        super(handler, mockSettings);
    }

    @Override
    public synchronized Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        return super.intercept(proxy, method, args, methodProxy);
    }
}

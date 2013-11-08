package org.kubek2k.mockito.spring.testbeans;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BeanToBeInjectedWithSpy {

    private BeanToBeSpiedOrMockedAndStubbed beanToBeSpiedOrMockedAndStubbed;

    public void callMethodWithoutArgumentsOnInjectedBeanTwice() {
        beanToBeSpiedOrMockedAndStubbed.methodWithoutArgument();
        beanToBeSpiedOrMockedAndStubbed.methodWithoutArgument();
    }

    @Resource
    public void setBeanToBeSpiedOrMockedAndStubbed(BeanToBeSpiedOrMockedAndStubbed beanToBeSpiedOrMockedAndStubbed) {
        this.beanToBeSpiedOrMockedAndStubbed = beanToBeSpiedOrMockedAndStubbed;
    }
}

package org.kubek2k.mockito.spring.testbeans;

import org.springframework.stereotype.Component;

@Component
public class BeanToBeSpiedOrMockedAndStubbed {

    public String methodWithArgument(String string) {
        return string;
    }

    public void methodWithoutArgument() {
    }

}

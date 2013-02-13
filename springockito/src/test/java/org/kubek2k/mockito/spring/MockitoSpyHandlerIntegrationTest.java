package org.kubek2k.mockito.spring;

import org.kubek2k.mockito.spring.testbeans.BeanToBeInjectedWithSpy;
import org.kubek2k.mockito.spring.testbeans.BeanToBeSpiedOrMockedAndStubbed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.annotation.Resource;
import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ContextConfiguration(locations = {"classpath*:/spring/parentContext.xml", "classpath*:/spring/mockitoSpyContext.xml"})
public class MockitoSpyHandlerIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private Date dateToBeSpied;

    @Resource
    private BeanToBeSpiedOrMockedAndStubbed beanToBeSpiedAndStubbed;


    @Resource
    BeanToBeInjectedWithSpy beanToBeInjectedWithSpy;


    @Test
    public void shouldLoadMockitoSpy() {
        dateToBeSpied.compareTo(new Date());
        verify(dateToBeSpied).compareTo(isA(Date.class));
    }

    @Test
    public void shouldAllowForSpiesTeBeStubbedUsingMockitoMatchers() {
        //given
        String fixedReturnValue = "fixedReturnValue";
        given(beanToBeSpiedAndStubbed.methodWithArgument(anyString()))
                .willReturn(fixedReturnValue);

        //when
        String returnedString = beanToBeSpiedAndStubbed.methodWithArgument("someString");

        //then
        assertThat(returnedString)
                .isEqualTo(fixedReturnValue);
    }

    @Test
    public void shouldInjectSpyOfTheBeanInsteadOfOriginalOneEverywhere() {
        //given
        int expectedAmountOfSpyMethodInvocations = 2;

        //when
        beanToBeInjectedWithSpy.callMethodWithoutArgumentsOnInjectedBeanTwice();

        //then
        verify(beanToBeSpiedAndStubbed, times(expectedAmountOfSpyMethodInvocations))
                .methodWithoutArgument();
    }
}

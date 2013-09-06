package org.kubek2k.springockito.general.basic.spy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.WrapWithSpy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:/spring/general/basic/spy/context.xml"})
public class WrapWithSpyBasic_Test {

    @WrapWithSpy
    @Autowired
    private BeanToBeSpiedOrMockedAndStubbed beanToBeSpiedAndStubbed;

    @Resource
    BeanToBeInjectedWithSpy beanToBeInjectedWithSpy;

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

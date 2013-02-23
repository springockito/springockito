package org.kubek2k.springockito.general.reset.annotation.spy.everymethod;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.experimental.junit.AbstractJUnit4SpringockitoContextTests;
import org.kubek2k.springockito.general.reset.MockedBean;
import org.kubek2k.tools.Ordered;
import org.kubek2k.tools.OrderedSpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(OrderedSpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:spring/general/reset/annotation/spy/everymethod/context.xml"})
public class DirtiesMocks2_Test extends AbstractJUnit4SpringockitoContextTests {

    @Resource(name = "mockedAndResetAfterClass")
    MockedBean firstBean;

    @Ordered(1)
    @Test
    public void checkMockExistAndIsWasReset() {
        //given
        verify(firstBean, times(0))
                .returnString(anyString());
    }
}

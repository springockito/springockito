package org.kubek2k.springockito.general.reset.xml.mock.onmethod;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.experimental.DirtiesMocks;
import org.kubek2k.springockito.annotations.experimental.junit.AbstractJUnit4SpringockitoContextTests;
import org.kubek2k.springockito.general.reset.MockedBean;
import org.kubek2k.tools.Ordered;
import org.kubek2k.tools.OrderedSpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(OrderedSpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/general/reset/xml/mock/onmethod/context.xml"})
public class DirtiesMocks_Test extends AbstractJUnit4SpringockitoContextTests {

    @Resource(name = "mockedAndReset")
    MockedBean firstBean;

    @Resource(name = "mockedAndNotReset")
    MockedBean secondBean;

    @Ordered(0)
    @Test
    @DirtiesMocks
    public void recoredSomeBehaviourAndResetIt() {
        given(firstBean.returnString("ala123"))
                .willReturn("bob123");

        String returnedString = firstBean.returnString("ala123");

        verify(firstBean, times(1))
                .returnString("ala123");
        assertThat(returnedString)
                .isEqualTo("bob123");
    }

    @Ordered(1)
    @Test
    public void checkThatFirstBeanMockWasReset() {

        String returnedString = firstBean.returnString("ala123");

        verify(firstBean, times(1))
                .returnString("ala123");
        assertThat(returnedString)
                .isEqualTo(null);
    }

    @Ordered(2)
    @Test
    public void stubFirstBeanAndDoNotResetIt() {
        given(secondBean.returnString("ala123"))
                .willReturn("bob123");

        String returnedString = secondBean.returnString("ala123");

        verify(secondBean, times(1))
                .returnString("ala123");
        assertThat(returnedString)
                .isEqualTo("bob123");
    }

    @Ordered(3)
    @Test
    public void checkThatBeanWasNotReset() {
        String returnedString = secondBean.returnString("ala123");

        verify(secondBean, times(2))
                .returnString("ala123");
        assertThat(returnedString)
                .isEqualTo("bob123");
    }

}

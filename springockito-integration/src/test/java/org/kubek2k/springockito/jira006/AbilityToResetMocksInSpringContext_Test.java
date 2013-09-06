package org.kubek2k.springockito.jira006;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.experimental.DirtiesMocks;
import org.kubek2k.springockito.annotations.experimental.DirtiesMocksTestContextListener;
import org.kubek2k.tools.Ordered;
import org.kubek2k.tools.OrderedSpringJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

import javax.annotation.Resource;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(OrderedSpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:spring/jira006/context.xml"})
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, DirtiesMocksTestContextListener.class})
public class AbilityToResetMocksInSpringContext_Test {

    @Resource(name = "firstBeanWhichProvesThatMocksAreResetWhenDirtiesMocksAnnotationIsUsed")
    @ReplaceWithMock
    MockedBean firstBean;

    @Resource(name = "secondBeanWhichProvesThatMocksAreNotResetWhenNoDirtiesMocksAnnotationIsUsed")
    @ReplaceWithMock
    MockedBean secondBean;

    @Ordered(0)
    @Test
    @DirtiesMocks
    public void shouldStubBehaviourAndDirtyMocks() {
        //given
        given(firstBean.returnString("ala123"))
                .willReturn("bob123");

        //when
        String returnedString = firstBean.returnString("ala123");

        //then
        verify(firstBean, times(1))
                .returnString("ala123");
        assertThat(returnedString)
                .isEqualTo("bob123");
    }

    @Ordered(1)
    @Test
    public void shouldOperateOnResetMockAndNotDirtyMocks() {
        //given
        //firstBean was reset

        //when
        String returnedString = firstBean.returnString("ala123");

        //then
        verify(firstBean, times(1))
                .returnString("ala123");
        assertThat(returnedString)
                .isEqualTo(null);
    }

    @Ordered(2)
    @Test
    public void shouldStubBehaviourAndOnPurposeNotDirtyMocks() {
        //given
        given(secondBean.returnString("ala123"))
                .willReturn("bob123");

        //when
        String returnedString = secondBean.returnString("ala123");

        //then
        verify(secondBean, times(1))
                .returnString("ala123");
        assertThat(returnedString)
                .isEqualTo("bob123");
    }

    @Ordered(3)
    @Test
    public void shouldOperateOnNotResetMockAndNotDirtyMocks() {
        //given
        //secondBean was not reset

        //when
        String returnedString = secondBean.returnString("ala123");

        //then
        verify(secondBean, times(2))
                .returnString("ala123");
        assertThat(returnedString)
                .isEqualTo("bob123");
    }

}

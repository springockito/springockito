package org.kubek2k.springockito.jira024;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = "classpath:spring/jira024/context.xml")
public class AbilityToStubXmlDefinedMocks_Test {

    @Autowired
    private SomeBean someBean;

    @Test
    public void shouldAllowToStubWithMockitoMatchers() {
        //given
        given(someBean.doSomething(anyString()))
                .willReturn("bob123");

        //when
        String returnValue = someBean.doSomething("ala123");

        //then
        assertThat(returnValue)
                .isEqualTo("bob123");
    }

    @Test
    public void shouldAllowToStubWithMockitoMatchersAnoterSyntax() {
        //given
        doReturn("eve123")
                .when(someBean).doSomething(anyString());

        //when
        String returnValue = someBean.doSomething("ala123");

        //then
        assertThat(returnValue)
                .isEqualTo("eve123");
    }
}

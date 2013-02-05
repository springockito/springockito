package org.kubek2k.springockito.jira025;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.tools.Jira;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.kubek2k.tools.TestUtil.isMock;

@Jira(number = 25, uri = "/kubek2k/springockito/issue/25/dirty-context-does-not-get-refreshed")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = "classpath:spring/jira025/context_full.xml")
public class FullContext_2_Test {

    @Autowired
    SomeBean someBean;

    @Autowired
    private SomeServiceInterface someServiceInterface;

    @Test
    public void shouldHaveMockInjected() {
        assertThat(isMock(someServiceInterface))
                .isFalse();
    }

    @Test
    public void shouldBeanFromContextHaveNotMockInjected() {
        SomeServiceInterface injected = someBean.getSomeServiceInterface();
        assertThat(injected)
                .isSameAs(someServiceInterface);
        assertThat(isMock(injected))
                .isFalse();
    }

}

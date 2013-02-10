package org.kubek2k.springockito.jira025;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.kubek2k.tools.TestUtil.isMock;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_CLASS;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = AFTER_CLASS)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = "classpath:spring/jira025/context_partial.xml")
public class ContextDirtyingPartialContext_1_Test {

    @Autowired
    SomeBean someBean;

    @Autowired
    @ReplaceWithMock
    private SomeServiceInterface someServiceInterface;

    @Test
    public void shouldHaveMockInjected() {
        assertThat(isMock(someServiceInterface))
                .isTrue();
    }

    @Test
    public void shouldBeanFromContextHaveMockInjected() {
        SomeServiceInterface injected = someBean.getSomeServiceInterface();
        assertThat(injected)
                .isSameAs(someServiceInterface);
        assertThat(isMock(injected))
                .isTrue();
    }

}



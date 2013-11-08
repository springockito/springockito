package org.kubek2k.springockito.jira011.spy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.WrapWithSpy;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.fest.assertions.Assertions.assertThat;
import static org.kubek2k.springockito.annotations.BeanNameStrategy.FIELD_NAME;
import static org.kubek2k.tools.TestUtil.isMock;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:spring/jira011/spy/context_5.xml"})
public class ExplicitBeanNameMoreImportantThenBeanNameStrategy_Test {

    @Resource(name = "beanWantingExplicitName")
    private BeanInjectedWith beanWantingExplicitName;

    @WrapWithSpy(beanName = "firstSpiedBeanArbitraryName", beanNameStrategy = FIELD_NAME)
    private FirstSpiedBean firstBean;

    @Test
    public void shouldMockBasedOnBeanName() {
        FirstSpiedBean injected = beanWantingExplicitName.getFirstSpiedBean();
        assertThat(isMock(injected))
                .isTrue();
    }
}

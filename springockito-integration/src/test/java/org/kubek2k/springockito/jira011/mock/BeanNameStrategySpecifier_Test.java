package org.kubek2k.springockito.jira011.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.fest.assertions.Assertions.assertThat;
import static org.kubek2k.springockito.annotations.BeanNameStrategy.FIELD_TYPE_NAME;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:spring/jira011/mock/context_2.xml"})
public class BeanNameStrategySpecifier_Test {

    @Resource
    BeanInjectedWith beanInjectedWith;

    @Resource
    @ReplaceWithMock(beanNameStrategy = FIELD_TYPE_NAME)
    private FirstBeanNotInContext firstBeanNotInContext;

    @Autowired
    @ReplaceWithMock(beanNameStrategy = FIELD_TYPE_NAME)
    private SecondBeanNotInContext secondBeanNotInContext;

    @Test
    public void shouldPutBeanInContextWithNameOfFieldNameWhenAutowiringByJavaxResource() {
        FirstBeanNotInContext injected = beanInjectedWith.getFirstBeanNotInContext();
        assertThat(injected)
                .isSameAs(firstBeanNotInContext);
    }

    @Test
    public void shouldPutBeanInContextWithNameOfFieldNameWhenAutowiringBySpringAutowired() {
        FirstBeanNotInContext injected = beanInjectedWith.getFirstBeanNotInContext();
        assertThat(injected)
                .isSameAs(firstBeanNotInContext);
    }
}

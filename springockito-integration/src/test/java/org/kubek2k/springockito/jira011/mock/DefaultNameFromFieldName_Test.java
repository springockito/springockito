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

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:spring/jira011/mock/context_1.xml"})
public class DefaultNameFromFieldName_Test {

    @Resource
    BeanInjectedWith beanInjectedWith;

    @Resource
    @ReplaceWithMock
    private FirstBeanNotInContext firstBeanNotInContext;

    @Autowired
    @ReplaceWithMock
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

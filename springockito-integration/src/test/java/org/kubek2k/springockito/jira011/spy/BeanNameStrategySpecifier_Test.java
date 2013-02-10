package org.kubek2k.springockito.jira011.spy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.WrapWithSpy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.fest.assertions.Assertions.assertThat;
import static org.kubek2k.springockito.annotations.BeanNameStrategy.FIELD_TYPE_NAME;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:spring/jira011/spy/context_2.xml"})
public class BeanNameStrategySpecifier_Test {

    @Resource
    BeanInjectedWith beanInjectedWith;

    @Resource
    @WrapWithSpy(beanNameStrategy = FIELD_TYPE_NAME)
    private FirstSpiedBean firstSpiedBean;

    @Autowired
    @WrapWithSpy(beanNameStrategy = FIELD_TYPE_NAME)
    private SecondSpiedBean secondSpiedBean;

    @Test
    public void shouldPutBeanInContextWithNameOfFieldNameWhenAutowiringByJavaxResource() {
        FirstSpiedBean injected = beanInjectedWith.getFirstSpiedBean();
        assertThat(injected)
                .isSameAs(firstSpiedBean);
    }

    @Test
    public void shouldPutBeanInContextWithNameOfFieldNameWhenAutowiringBySpringAutowired() {
        FirstSpiedBean injected = beanInjectedWith.getFirstSpiedBean();
        assertThat(injected)
                .isSameAs(firstSpiedBean);
    }
}

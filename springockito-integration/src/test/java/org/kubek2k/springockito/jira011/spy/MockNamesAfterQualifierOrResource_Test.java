package org.kubek2k.springockito.jira011.spy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.WrapWithSpy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.fest.assertions.Assertions.assertThat;
import static org.kubek2k.tools.TestUtil.isMock;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:spring/jira011/spy/context_1.xml"})
public class MockNamesAfterQualifierOrResource_Test {

    @Resource
    BeanInjectedWith beanInjectedWith;

    @Autowired
    @Qualifier("firstSpiedBean")
    @WrapWithSpy
    private FirstSpiedBean firstBean;

    @Resource(name = "secondSpiedBean")
    @WrapWithSpy
    private SecondSpiedBean secondBean;

    @Test
    public void shouldCreateMocksWhenNoSuchDefinitionsInSuppliedLocationAndInjectThem() {
        assertThat(isMock(firstBean))
                .isTrue();
        assertThat(isMock(secondBean))
                .isTrue();
        assertThat(firstBean)
                .isNotSameAs(secondBean);
    }

    @Test
    public void shouldPutBeanInContextWithNameFromSpringQualifierValue() {
        FirstSpiedBean injected = beanInjectedWith.getFirstSpiedBean();
        assertThat(injected)
                .isSameAs(firstBean);
    }

    @Test
    public void shouldPutBeanInContextWithNameFromJavaxResourceName() {
        SecondSpiedBean injected = beanInjectedWith.getSecondSpiedBean();
        assertThat(injected)
                .isSameAs(secondBean);
    }
}

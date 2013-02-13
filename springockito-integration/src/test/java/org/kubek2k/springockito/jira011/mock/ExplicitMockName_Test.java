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
import static org.kubek2k.tools.TestUtil.isMock;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:spring/jira011/mock/context_3.xml"})
public class ExplicitMockName_Test {

    @Resource
    BeanInjectedWith beanInjectedWith;

    @Autowired
    @ReplaceWithMock(beanName = "firstBeanNotInContextArbitraryName")
    private FirstBeanNotInContext firstBean;

    @Resource
    @ReplaceWithMock(beanName = "secondBeanNotInContextArbitraryName")
    private SecondBeanNotInContext secondBean;

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
        FirstBeanNotInContext injected = beanInjectedWith.getFirstBeanNotInContext();
        assertThat(injected)
                .isSameAs(firstBean);
    }

    @Test
    public void shouldPutBeanInContextWithNameFromJavaxResourceName() {
        SecondBeanNotInContext injected = beanInjectedWith.getSecondBeanNotInContext();
        assertThat(injected)
                .isSameAs(secondBean);
    }
}

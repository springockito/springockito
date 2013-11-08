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
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:spring/jira011/spy/context_6.xml"})
public class GetMockNameFromSpringockitoFirst_Test {

    @Resource
    BeanInjectedWith beanInjectedWith;

    @Autowired
    @Qualifier("firstBeanUnwantedName")
    @WrapWithSpy(beanName = "firstSpiedBeanArbitraryName")
    private FirstSpiedBean firstBean;

    @Resource(name = "secondBeanUnwantedName")
    @WrapWithSpy(beanName = "secondSpiedBeanArbitraryName")
    private SecondSpiedBean secondBean;

    @Test
    public void shouldFirstAndSecondBeanBeDifferentAndNotMocks() {
        assertThat(isMock(firstBean))
                .isFalse();
        assertThat(isMock(secondBean))
                .isFalse();
        assertThat(firstBean)
                .isNotSameAs(secondBean);
    }

    @Test
    public void shouldBeansInjectedIntoBeanBeDifferentAndMocks() {
        FirstSpiedBean injectedFirst = beanInjectedWith.getFirstSpiedBean();
        SecondSpiedBean injectedSecond = beanInjectedWith.getSecondSpiedBean();
        assertThat(isMock(injectedFirst))
                .isTrue();
        assertThat(isMock(injectedSecond))
                .isTrue();
        assertThat(injectedFirst)
                .isNotSameAs(injectedSecond);
    }

    @Test
    public void shouldUseExplicitNameFromReplaceWithMockInsteadOfSpringQualifierValue() {
        FirstSpiedBean injected = beanInjectedWith.getFirstSpiedBean();
        assertThat(injected)
                .isNotSameAs(firstBean);
    }

    @Test
    public void shouldUseExplicitNameFromReplaceWithMockInsteadOfJavaxResourceName() {
        SecondSpiedBean injected = beanInjectedWith.getSecondSpiedBean();
        assertThat(injected)
                .isNotSameAs(secondBean);
    }


}

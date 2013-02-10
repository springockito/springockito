package org.kubek2k.springockito.jira011.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
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
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:spring/jira011/mock/context_3.xml"})
public class GetMockNameFromSpringockitoFirst_Test {

    @Resource
    BeanInjectedWith beanInjectedWith;

    @Autowired
    @Qualifier("firstBeanUnwantedName")
    @ReplaceWithMock(beanName = "firstBeanNotInContextArbitraryName")
    private FirstBeanNotInContext firstBean;

    @Resource(name = "secondBeanUnwantedName")
    @ReplaceWithMock(beanName = "secondBeanNotInContextArbitraryName")
    private SecondBeanNotInContext secondBean;

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
        FirstBeanNotInContext injectedFirst = beanInjectedWith.getFirstBeanNotInContext();
        SecondBeanNotInContext injectedSecond = beanInjectedWith.getSecondBeanNotInContext();
        assertThat(isMock(injectedFirst))
                .isTrue();
        assertThat(isMock(injectedSecond))
                .isTrue();
        assertThat(injectedFirst)
                .isNotSameAs(injectedSecond);
    }

    @Test
    public void shouldUseExplicitNameFromReplaceWithMockInsteadOfSpringQualifierValue() {
        FirstBeanNotInContext injected = beanInjectedWith.getFirstBeanNotInContext();
        assertThat(injected)
                .isNotSameAs(firstBean);
    }

    @Test
    public void shouldUseExplicitNameFromReplaceWithMockInsteadOfJavaxResourceName() {
        SecondBeanNotInContext injected = beanInjectedWith.getSecondBeanNotInContext();
        assertThat(injected)
                .isNotSameAs(secondBean);
    }


}

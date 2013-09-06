package org.kubek2k.springockito.annotations.it;

import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.WrapWithSpy;
import org.kubek2k.springockito.annotations.it.beans.InnerBean;
import org.kubek2k.springockito.annotations.it.beans.OuterBeanWithAutowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;

@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = "classpath:/componentScanMockContext.xml")
public class SpringockitoAnnotationsComponentScanSpiesIntegrationTest extends AbstractTestNGSpringContextTests {

    @WrapWithSpy
    @Autowired
    private InnerBean innerBean;

    @Autowired
    private OuterBeanWithAutowired outerBean;

    @Test
    @DirtiesContext
    public void shouldUseMockInsteadOfOriginalBean() {
        int result = outerBean.doSomething();

        Assert.assertEquals(InnerBean.VALUE_RETURNED_BY_INNER, result);
        verify(innerBean).doSomething();
    }
}

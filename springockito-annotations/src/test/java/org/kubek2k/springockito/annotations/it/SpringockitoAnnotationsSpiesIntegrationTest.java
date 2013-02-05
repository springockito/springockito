package org.kubek2k.springockito.annotations.it;

import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.WrapWithSpy;
import org.kubek2k.springockito.annotations.it.beans.InnerBean;
import org.kubek2k.springockito.annotations.it.beans.OuterBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;

@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = "classpath:/mockContext.xml")
public class SpringockitoAnnotationsSpiesIntegrationTest extends AbstractTestNGSpringContextTests {

    @WrapWithSpy
    @Autowired
    private InnerBean innerBean;

    @Autowired
    private OuterBean outerBean;

    @Test
    @DirtiesContext
    public void shouldUseSpyInsteadOfOriginalBean() {
        int result = outerBean.doSomething();

        // the actual returned value is real and we can verify interaction
        Assert.assertEquals(result, InnerBean.VALUE_RETURNED_BY_INNER);
        verify(innerBean).doSomething();
    }

}
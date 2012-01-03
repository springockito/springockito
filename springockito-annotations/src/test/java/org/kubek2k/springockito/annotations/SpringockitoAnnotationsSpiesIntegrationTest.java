package org.kubek2k.springockito.annotations;

import static org.mockito.Mockito.verify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

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
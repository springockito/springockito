package org.kubek2k.springockito.annotations;

import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(loader = SpringockitoContextLoader.class,
locations = "classpath:/mockContext.xml")
public class SpringockitoAnnotationsSpiesIntegrationTest extends AbstractJUnit4SpringContextTests {

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
        Assert.assertEquals(InnerBean.VALUE_RETURNED_BY_INNER, result);
        verify(innerBean).doSomething();
    }
    
}
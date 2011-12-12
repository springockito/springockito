package org.kubek2k.springockito.annotations;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import static org.mockito.Mockito.verify;

@ContextConfiguration(loader = SpringockitoContextLoader.class,
locations = "classpath:/componentScanMockContext.xml")
public class SpringockitoAnnotationsComponentScanSpiesIntegrationTest extends AbstractJUnit4SpringContextTests {
    
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

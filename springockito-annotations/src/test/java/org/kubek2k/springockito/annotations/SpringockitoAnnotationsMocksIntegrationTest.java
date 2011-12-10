package org.kubek2k.springockito.annotations;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(loader = SpringockitoContextLoader.class,
locations = "classpath:/mockContext.xml")
public class SpringockitoAnnotationsMocksIntegrationTest extends AbstractJUnit4SpringContextTests {
    
    @ReplaceWithMock
    @Autowired
    private InnerBean innerBean;
    
    @Autowired
    private OuterBean outerBean;
    
    @Test
    @DirtiesContext
    public void shouldUseMockInsteadOfOriginalBean() {
        outerBean.doSomething();
        
        verify(innerBean).doSomething();
    }
    
}
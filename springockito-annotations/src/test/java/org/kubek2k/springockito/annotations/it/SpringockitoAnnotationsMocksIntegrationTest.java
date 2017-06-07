package org.kubek2k.springockito.annotations.it;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.Stub;
import org.kubek2k.springockito.annotations.it.beans.InnerBean;
import org.kubek2k.springockito.annotations.it.beans.OuterBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(loader = SpringockitoContextLoader.class,
        locations = "classpath:/mockContext.xml")
public class SpringockitoAnnotationsMocksIntegrationTest extends AbstractTestNGSpringContextTests {
    private static final int TEST_RETURN_VALUE = 1234;
    
    @ReplaceWithMock
    @Autowired
    private InnerBean innerBean;

    @Autowired
    private OuterBean outerBean;
    
    @Stub("innerBean")
    public static void mockBehavior(InnerBean mockedInnerBeanInstance) {
        when(mockedInnerBeanInstance.doSomething()).thenReturn(TEST_RETURN_VALUE);
    }

    @Test
    @DirtiesContext
    public void shouldUseMockInsteadOfOriginalBean() {
        outerBean.doSomething();

        verify(innerBean).doSomething();
    }

    @Test
    @DirtiesContext
    public void shouldDefineMockedBehaviorForMock() {
        int value = innerBean.doSomething();

        assertEquals(TEST_RETURN_VALUE, value);

        verify(innerBean).doSomething();
        verifyNoMoreInteractions(innerBean);
    }
}
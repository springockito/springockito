package org.kubek2k.springockito.annotations;

import static org.mockito.Mockito.verify;

import junit.framework.Assert;
import org.mockito.Answers;
import org.mockito.internal.invocation.realmethod.CGLIBProxyRealMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = "classpath:/componentScanMockContext.xml")
public class SpringockitoAnnotationsComponentScanMocksIntegrationTest extends AbstractTestNGSpringContextTests {

    @ReplaceWithMock(extraInterfaces = {Runnable.class}, defaultAnswer = Answers.RETURNS_MOCKS)
    @Autowired
    private InnerBean innerBean;

    @Autowired
    private OuterBeanWithAutowired outerBean;

    @Test
    @DirtiesContext
    public void shouldUseMockInsteadOfOriginalBean() {
        outerBean.doSomething();

        verify(innerBean).doSomething();
    }

    @Test
    @DirtiesContext
    public void shouldGiveBeanAnAdditionalInterface() {
        Assert.assertTrue(innerBean instanceof Runnable);
    }

    @Test
    @DirtiesContext
    public void shouldConfigureBeanWithMockDefaultAnswer() {
        // shouldn't fail - there's a mock returned
        innerBean.methodReturningInteger().run();
    }


}

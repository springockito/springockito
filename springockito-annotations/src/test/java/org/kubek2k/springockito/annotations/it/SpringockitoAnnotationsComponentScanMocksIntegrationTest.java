package org.kubek2k.springockito.annotations.it;

import junit.framework.Assert;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.it.beans.InnerBean;
import org.kubek2k.springockito.annotations.it.beans.OuterBeanWithAutowired;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;

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

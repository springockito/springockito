package org.kubek2k.springockito.annotations;

import org.mockito.Answers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = "classpath:/componentScanMockContext.xml")
public class SpringockitoAnnotationsComponentScanSpyAutoAutowiringIntegrationTest extends AbstractTestNGSpringContextTests {

    @WrapWithSpy
    private InnerBean innerBean;

    @Test
    public void shouldAutoAutowireSpy() {
        org.testng.Assert.assertNotNull(innerBean);
    }

}

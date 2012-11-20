package org.kubek2k.springockito.annotations;

import org.mockito.Answers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;

@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = "classpath:/componentScanMockContext.xml")
public class SpringockitoAnnotationsComponentScanMockAutoAutowiringIntegrationTest extends AbstractTestNGSpringContextTests {

    @ReplaceWithMock(extraInterfaces = {Runnable.class}, defaultAnswer = Answers.RETURNS_MOCKS)
    private InnerBean innerBean;

    @Test
    public void shouldAutoAutowireMock() {
        org.testng.Assert.assertNotNull(innerBean);
    }

}

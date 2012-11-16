package org.kubek2k.springockito.annotations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;

@ContextConfiguration(loader = SpringockitoContextLoader.class, classes = AnnotationBasedConfigurationIntegrationTest.Config.class)
@LoadContextWith(AnnotationConfigContextLoader.class)
public class AnnotationBasedConfigurationIntegrationTest extends AbstractTestNGSpringContextTests {

    @Configuration
    static class Config {
        @Bean(name = "outerBean")
        public OuterBean outerBean() {
            OuterBean outerBean = new OuterBean();

            outerBean.setInnerBean(innerBean());

            return outerBean;
        }

        @Bean(name = "innerBean")
        public InnerBean innerBean() {
            return new InnerBean();
        }
    }

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

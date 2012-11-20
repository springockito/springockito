package org.kubek2k.springockito.annotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import org.springframework.context.ApplicationContext;
import org.testng.annotations.Test;

public class SpringockitoContextLoaderTest {
    @Test
    public void shouldLoadMockBean() throws Exception {
        // given
        SpringockitoContextLoader loader = new SpringockitoContextLoader();

        // when
        loader.processLocations(SomeTestClass.class);
        ApplicationContext context = loader.loadContext("classpath:/mockContext.xml");

        // then
        OuterBean outerBean = (OuterBean) context.getBean("outerBean1");

        // verification that it's a mock
        verifyNoMoreInteractions(outerBean);

    }
    
    @Test
    public void shouldLoadSpyBean() throws Exception {
        // given
        SpringockitoContextLoader loader = new SpringockitoContextLoader();

        // when
        loader.processLocations(SomeTestClass.class);
        ApplicationContext context = loader.loadContext("classpath:/mockContext.xml");

        // then
        OuterBean outer =  (OuterBean) context.getBean("outerBean");
        outer.doSomething();
        
        // verification that it's a spy
        verify(outer).doSomething();
    }

    public static class SomeTestClass {
        @SuppressWarnings("unused")
        @ReplaceWithMock
        private OuterBean outerBean1;
        
        @SuppressWarnings("unused")
        @WrapWithSpy
        private OuterBean outerBean;
    }


    public static interface X {
        public int hello();
    }

}

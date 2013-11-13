package org.kubek2k.springockito.annotations;

import org.kubek2k.springockito.annotations.it.beans.OuterBean;
import org.springframework.context.ApplicationContext;
import org.testng.annotations.Test;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

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
        OuterBean outer = (OuterBean) context.getBean("outerBean");
        outer.doSomething();

        // verification that it's a spy
        verify(outer).doSomething();
    }

    @Test
    public void shouldLoadMockBeanFormAbstractTestClass() throws Exception {
        // given
        SpringockitoContextLoader loader = new SpringockitoContextLoader();

        // when
        loader.processLocations(SomeTestClass.class);
        ApplicationContext context = loader.loadContext("classpath:/mockContext.xml");

        // then
        OuterBean outerBean = (OuterBean) context.getBean("outerBean2");

        // verification that it's a mock
        verifyNoMoreInteractions(outerBean);

    }

    public static class SomeTestClass extends SomeAbstractTestClass {
        @SuppressWarnings("unused")
        @WrapWithSpy
        private OuterBean outerBean;

        @SuppressWarnings("unused")
        @ReplaceWithMock
        private OuterBean outerBean1;
    }

    public static class SomeAbstractTestClass {
        @SuppressWarnings("unused")
        @ReplaceWithMock
        protected OuterBean outerBean2;
    }

    public static interface X {
        public int hello();
    }

}

package org.kubek2k.mockito.spring;

import org.kubek2k.mockito.spring.testbeans.BeanToBeSpiedOrMockedAndStubbed;
import org.mockito.cglib.proxy.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.annotation.Resource;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;


@ContextConfiguration(locations = {"classpath*:/spring/mockitoContext.xml"})
public class MockitoMockHandlerIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SomeFancyClass someFancyClass;

    @Autowired
    @Qualifier("someFancyClass")
    private SomeFancyClass someFancyClass2;

    @Resource
    private BeanToBeSpiedOrMockedAndStubbed beanToBeMockedAndStubbed;

    @Test
    public void shouldLoadMockitoMock() {
        assertNotNull(someFancyClass);
        assertTrue(someFancyClass instanceof Factory);

        assertNotNull(someFancyClass2);
        assertTrue(someFancyClass2 instanceof Factory);

    }

    @Test
    public void shouldAllowForMocksTeBeStubbedUsingMockitoMatchers() {
        //given
        String fixedReturnValue = "fixedReturnValue";
        given(beanToBeMockedAndStubbed.methodWithArgument(anyString()))
                .willReturn(fixedReturnValue);

        //when
        String returnedString = beanToBeMockedAndStubbed.methodWithArgument("someString");

        //then
        assertThat(returnedString)
                .isEqualTo(fixedReturnValue);
    }
}

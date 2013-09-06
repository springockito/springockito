package org.kubek2k.mockito.spring;

import static org.testng.Assert.assertNotNull;

import org.kubek2k.mockito.spring.testbeans.BeanAutowiringMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations={"classpath:spring/mockitoComponentScanContext.xml"})
public class MockitoMockHandlerComponentScanIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private BeanAutowiringMock beanAutowiringMock;

    @Test
    public void shouldAutowireMocksInSpringBeansWhenUsingComponentScan() {
        assertNotNull(beanAutowiringMock);
        assertNotNull(beanAutowiringMock.getInterfaceToBeMocked());
    }
}

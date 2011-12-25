package org.kubek2k.mockito.spring;

import org.kubek2k.mockito.spring.testbeans.BeanAutowiringMock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import static org.testng.Assert.assertNotNull;

//Test case for issue 3: https://bitbucket.org/kubek2k/springockito/issue/3/problem-with-injecting-mockito-mock-by
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

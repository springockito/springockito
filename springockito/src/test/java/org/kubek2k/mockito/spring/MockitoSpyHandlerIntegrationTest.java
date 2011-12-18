package org.kubek2k.mockito.spring;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.verify;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "classpath*:/spring/parentContext.xml", "classpath*:/spring/mockitoSpyContext.xml" })
public class MockitoSpyHandlerIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private Date dateToBeSpied;
    
    @Test
    public void shouldLoadMockitoSpy() {
        dateToBeSpied.compareTo(new Date());
        verify(dateToBeSpied).compareTo(isA(Date.class));
    }
}

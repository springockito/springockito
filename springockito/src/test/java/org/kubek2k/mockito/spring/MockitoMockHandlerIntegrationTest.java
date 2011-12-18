package org.kubek2k.mockito.spring;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.mockito.cglib.proxy.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;


@ContextConfiguration(locations={"classpath*:/spring/mockitoContext.xml"})
public class MockitoMockHandlerIntegrationTest extends AbstractTestNGSpringContextTests {
    
    @Autowired
    private Date someDate;
    
    @Test
    public void shouldLoadMockitoMock() {
        assertNotNull(someDate);
        assertTrue(someDate instanceof Factory);
    }
}

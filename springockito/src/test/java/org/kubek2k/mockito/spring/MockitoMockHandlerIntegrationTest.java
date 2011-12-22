package org.kubek2k.mockito.spring;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Date;

import org.mockito.cglib.proxy.Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;


@ContextConfiguration(locations={"classpath*:/spring/mockitoContext.xml"})
public class MockitoMockHandlerIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private SomeFancyClass someFancyClass;
    
    @Autowired
    @Qualifier("someFancyClass")
    private SomeFancyClass someFancyClass2;
    
    @Test
    public void shouldLoadMockitoMock() {
        assertNotNull(someFancyClass);
        assertTrue(someFancyClass instanceof Factory);
        
        assertNotNull(someFancyClass2);
        assertTrue(someFancyClass2 instanceof Factory);

    }
}

package org.kubek2k.springockito.annotations.contextcache;

import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.springockito.annotations.it.beans.InnerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = "classpath:/componentScanMockContext.xml", loader = SpringockitoContextLoader.class)
public class SpringockitoIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    @ReplaceWithMock
    private InnerBean innerBean;

    @Test
    public void shouldBeAnActualBean() {
        org.testng.Assert.assertFalse(innerBean.getClass().equals(InnerBean.class));
    }
}

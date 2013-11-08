package org.kubek2k.springockito.annotations.contextcache;

import junit.framework.Assert;
import org.kubek2k.springockito.annotations.it.beans.InnerBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@ContextConfiguration(locations = {"classpath:/componentScanMockContext.xml", "classpath*:mock=org.kubek2k.springockito"})
public class NoSpringockitoIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private InnerBean innerBean;

    @Test
    public void shouldBeAnActualBean() {
        Assert.assertEquals(InnerBean.class, innerBean.getClass());
    }
}

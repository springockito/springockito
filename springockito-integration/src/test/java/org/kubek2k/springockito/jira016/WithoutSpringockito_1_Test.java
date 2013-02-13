package org.kubek2k.springockito.jira016;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.kubek2k.tools.TestUtil.isMock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/jira016/context.xml")
public class WithoutSpringockito_1_Test {

    @Autowired
    private SomeBean bean;

    @Test
    public void shouldNotBeMock() {
        assertThat(isMock(bean))
                .isFalse();
    }

}
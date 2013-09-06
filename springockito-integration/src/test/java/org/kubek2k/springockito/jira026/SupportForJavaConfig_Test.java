package org.kubek2k.springockito.jira026;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoAnnotatedContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.fest.assertions.Assertions.assertThat;
import static org.kubek2k.tools.TestUtil.isMock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoAnnotatedContextLoader.class)
public class SupportForJavaConfig_Test {

    @Configuration
    static class Config {
        @Bean
        SomeBean someBean() {
            return new SomeBean();
        }
    }

    @ReplaceWithMock
    @Autowired
    SomeBean someBean;

    @Test
    public void shouldHaveMockInjected() {
        assertThat(someBean)
                .isNotNull();
        assertThat(isMock(someBean))
                .isTrue();
    }
}
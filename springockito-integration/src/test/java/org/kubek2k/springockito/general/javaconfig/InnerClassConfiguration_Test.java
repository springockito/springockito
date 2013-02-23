package org.kubek2k.springockito.general.javaconfig;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoAnnotatedContextLoader;
import org.kubek2k.springockito.annotations.WrapWithSpy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.kubek2k.tools.TestUtil.isMock;

@DirtiesContext
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoAnnotatedContextLoader.class)
public class InnerClassConfiguration_Test {

    @Configuration
    static class Config {
        @Bean
        SomeBean someBean() {
            return new SomeBean();
        }

        @Bean
        SpiedBean spiedBean(){
            return new SpiedBean();
        }
    }



    @ReplaceWithMock
    @Autowired
    SomeBean someBean;

    @WrapWithSpy
    @Autowired
    SpiedBean spiedBean;

    @Test
    public void shouldMock() {
        assertThat(someBean)
                .isNotNull();
        assertThat(isMock(someBean))
                .isTrue();
    }

//    @Test
    public void shouldSpy() {
        assertThat(spiedBean)
                .isNotNull();
        assertThat(isMock(spiedBean))
                .isTrue();
    }
}

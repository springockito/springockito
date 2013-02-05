package org.kubek2k.springockito.jira016;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.kubek2k.tools.Jira;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.kubek2k.tools.TestUtil.isMock;

@Jira(number = 16, uri = "/kubek2k/springockito/issue/16/springockito-gets-incorrectly-cached-when")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = "classpath:spring/jira016/context.xml")
public class WithSpringockito_2_Test {

    @Autowired
    @ReplaceWithMock
    private SomeBean bean;

    @Test
    public void shouldBeMockEvenIfEarlierTestWithTheSameLocationsHasItAsNoMock() {

        assertThat(isMock(bean))
                .isTrue();
    }

}
package org.kubek2k.springockito.general.contextcaching;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.mockito.internal.util.MockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:spring/general/contextcaching/context.xml"})
public class WithSpringockito_3_Test {

    @Autowired
    private BeanTestingContextCaching bean;

    @Test
    public void shouldGetMockFromCachedContext() {
        //given
        //as mock from cached context should have behaviour recorded

        //when
        bean.incrementState();

        //then
        assertThat(bean.getState())
                .isEqualTo(2);
        assertThat(new MockUtil().isMock(bean))
                .isTrue();
    }
}
package org.kubek2k.springockito.general.contextcaching;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.fest.assertions.Assertions.assertThat;
import static org.kubek2k.tools.TestUtil.isMock;
import static org.mockito.BDDMockito.doCallRealMethod;
import static org.mockito.BDDMockito.given;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = "classpath:spring/general/contextcaching/context.xml")
public class WithSpringockito_1_Test {

    @Autowired
    @ReplaceWithMock
    private BeanTestingContextCaching bean;

    @Test
    public void shouldChangeStateOfMock() {
        //given
        doCallRealMethod()
                .when(bean).incrementState();
        given(bean.getState())
                .willCallRealMethod();

        //when
        bean.incrementState();

        //then
        assertThat(bean.getState())
                .isEqualTo(1);
        assertThat(isMock(bean))
                .isTrue();
    }

}
package org.kubek2k.springockito.annotations.experimental.junit;

import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.experimental.DirtiesMocksTestContextListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;


@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class, DirtiesMocksTestContextListener.class})
public abstract class AbstractJUnit4SpringockitoContextTests extends AbstractJUnit4SpringContextTests {
}

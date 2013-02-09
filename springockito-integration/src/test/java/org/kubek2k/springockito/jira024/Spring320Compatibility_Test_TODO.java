package org.kubek2k.springockito.jira024;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.kubek2k.tools.Jira;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@Ignore
@Jira(number = 24, uri = "/kubek2k/springockito/issue/24/springockitocontextloader-not-compatible")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/jira024/context.xml")
public class Spring320Compatibility_Test_TODO {
}

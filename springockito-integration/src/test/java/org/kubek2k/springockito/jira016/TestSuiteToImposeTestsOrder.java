package org.kubek2k.springockito.jira016;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.kubek2k.tools.Jira;

@Jira(number = 16, uri = "/kubek2k/springockito/issue/16/springockito-gets-incorrectly-cached-when")
@RunWith(Suite.class)
@Suite.SuiteClasses({WithoutSpringockito_1_Test.class, WithSpringockito_2_Test.class})
public class TestSuiteToImposeTestsOrder {
}

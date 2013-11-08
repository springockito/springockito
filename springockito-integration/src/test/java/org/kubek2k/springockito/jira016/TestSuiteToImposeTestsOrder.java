package org.kubek2k.springockito.jira016;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({WithoutSpringockito_1_Test.class, WithSpringockito_2_Test.class})
public class TestSuiteToImposeTestsOrder {
}

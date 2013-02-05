package org.kubek2k.springockito.jira025;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.kubek2k.tools.Jira;

@Jira(number = 25, uri = "/kubek2k/springockito/issue/25/dirty-context-does-not-get-refreshed")
@RunWith(Suite.class)
@Suite.SuiteClasses({ContextDirtyingPartialContext_1_Test.class, FullContext_2_Test.class})
public class TestSuiteToImposeTestsOrder {
}

### Springockito

This is a small extension to Spring that simplifies way of creation mockito mocks in the integration tests' related context xml files.

### Mocking

Having a definition in context definition file, such as:
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
...
   <bean id="accountService" class="org.kubek2k.account.DefaultAccountService" />
   <bean id="bank" class="org.kubek2k.bank.Bank">
      <property name="accountService" ref="accountService" />
   </bean>
...
</beans>
```

you can simply override it by mock with the definition in file that is loaded only within tests, ex:
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:mockito="http://www.mockito.org/spring/mockito"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
    http://www.mockito.org/spring/mockito http://www.mockito.org/spring/mockito.xsd">
...
    <mockito:mock id="accountService" class="org.kubek2k.account.DefaultAccountService" />
..
</beans>
```

### Spying

Sometimes you are in need of plugin into the established application context to check some action happened on some beans under certain conditions, but you don't want bother recording the actual behavior of beans. Here the spying comes in handy:

Having definition:

```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd">
...
    <bean id="accountService" class="DefaultAccountService" />
...
</beans>
```

we can spy the service within integration test using definition:
```xml
<beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
    http://www.mockito.org/spring/mockito http://www.mockito.org/spring/mockito.xsd">
...
     <mockito:spy beanName="accountService" />
...
</beans>
```

and in test we can do something like:

```java

@Autowired
private AccountService accountService;

@Test
public void shouldCountInterestForDeposit() {
    // given
    Deposit deposit = new Deposit(12);
    bank.addDeposit(deposit);

    // when
    bank.endOfTheMonth();
    
    // then
    verify(accountService).countInterestFor(deposit);
}

```

hope you like it :)


### Maven2 usage

Springockito is available from Maven Central Repository and to use it add following dependency to your pom.xml:

```xml
<dependencies>
     ...
     <dependency>
       <groupId>org.kubek2k</groupId>
       <artifactId>springockito</artifactId>
       <version>1.0.9</version>
       <scope>test</scope>
     </dependency>
     ...
</dependencies>
```

### Springockito-annotations
This is a subproject of Springockito that pushes the idea even further, ex:
```java
@ContextConfiguration(loader = SpringockitoContextLoader.class,
locations = "classpath:/context.xml")
public class SpringockitoAnnotationsMocksIntegrationTest extends AbstractJUnit4SpringContextTests {
    
    @ReplaceWithMock
    @Autowired
    private InnerBean innerBean;

    @WrapWithSpy
    @Autowired
    private AnotherInnerBean anotherInnerBean;
     ....
}
```
and your mocks are ready to go - no need to write test-specific xml context files. More info here http://springockito.github.io/springockito/


### License
License is distributed under MIT license
Copyright (c) 2011 springockito.org

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

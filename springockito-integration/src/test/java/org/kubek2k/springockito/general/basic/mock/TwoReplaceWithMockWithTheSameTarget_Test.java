package org.kubek2k.springockito.general.basic.mock;


import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kubek2k.springockito.annotations.BeanNameStrategy;
import org.kubek2k.springockito.annotations.ReplaceWithMock;
import org.kubek2k.springockito.annotations.SpringockitoContextLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = SpringockitoContextLoader.class, locations = {"classpath:spring/general/basic/mock/context.xml"})
public class TwoReplaceWithMockWithTheSameTarget_Test {

    @Autowired
    @ReplaceWithMock(beanName = "theSameName")
    private SomeBean someBean;

    @ReplaceWithMock(beanName = "theSameName")
    private SomeBean someBean1;


    @Test
    public void youShouldCheckLogs(){
        //NOTE: two mocks are defined via annatation with the same name
        //NOTE: you should look into logs of this test is make sure that there's no bean overriding at the spring level
        //NOTO: spring bean overriding log line: 'INFO: Overriding bean definition for bean 'theSameName': replacing [Generic bean: class [org.kubek2k.springockito.core.internal.mock.MockFactorySpringockito]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=true; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null] with [Generic bean: class [org.kubek2k.springockito.core.internal.mock.MockFactorySpringockito]; scope=; abstract=false; lazyInit=false; autowireMode=0; dependencyCheck=0; autowireCandidate=true; primary=true; factoryBeanName=null; factoryMethodName=null; initMethodName=null; destroyMethodName=null]'
        //NOTE: springockito should handle this
        //TODO: log at springockito level, alternatively springockito should throw exception?
        Assertions.assertThat(someBean)
                .isNotNull();
    }
}

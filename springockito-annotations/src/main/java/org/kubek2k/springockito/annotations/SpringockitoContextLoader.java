package org.kubek2k.springockito.annotations;

import java.util.*;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.support.GenericXmlContextLoader;

public class SpringockitoContextLoader extends GenericXmlContextLoader {

    private Map<String, DesiredMockitoBeansFinder.MockProperties<ReplaceWithMock>> mockedBeans
            = new HashMap<String, DesiredMockitoBeansFinder.MockProperties<ReplaceWithMock>>();
    private Set<String> spiedBeans;

    private DesiredMockitoBeansFinder mockedBeansFinder = new DesiredMockitoBeansFinder();

    private MockitoBeansDefiner mockitoBeansDefiner = new MockitoBeansDefiner();
    private MockitoSpiesDefiner mockitoSpiesDefiner = new MockitoSpiesDefiner();

    @Override
    protected void customizeContext(GenericApplicationContext context) {
        super.customizeContext(context);

        context.registerBeanDefinition("springockitoAutowiredBeanPostProcessor", BeanDefinitionBuilder.genericBeanDefinition(AutowiredAnnotationBeanPostProcessor.class)
                .addPropertyValue("autowiredAnnotationTypes", new HashSet<Class>(Arrays.asList(ReplaceWithMock.class, WrapWithSpy.class)))
                .getBeanDefinition());

        registerMocks(context, mockedBeans);
        registerSpies(context, spiedBeans);
    }

    private void registerMocks(GenericApplicationContext context,
                               Map<String, DesiredMockitoBeansFinder.MockProperties<ReplaceWithMock>> mockedBeans) {
        for (Map.Entry<String, DesiredMockitoBeansFinder.MockProperties<ReplaceWithMock>> beanEntry : this.mockedBeans.entrySet()) {
            DesiredMockitoBeansFinder.MockProperties<ReplaceWithMock> mockProperties = beanEntry.getValue();
            ReplaceWithMock replaceWithMockAnnotation = mockProperties.getAnnotationValues();
            context.registerBeanDefinition(beanEntry.getKey(),
                    mockitoBeansDefiner.createMockFactoryBeanDefinition(mockProperties.getMockClass(),
                            replaceWithMockAnnotation.extraInterfaces(),
                            replaceWithMockAnnotation.name(),
                            replaceWithMockAnnotation.defaultAnswer()
                    ));
        }
    }

    private void registerSpies(GenericApplicationContext context, Set<String> spiedBeanNames) {
        for (String beanName : spiedBeanNames) {
            BeanDefinition beanDefinition = context.getBeanDefinition(beanName);
            String wrappedBeanName = beanName + "$$WRAPPED_WITH_SPY";
            context.registerBeanDefinition(wrappedBeanName, beanDefinition);
            context.registerBeanDefinition(beanName, mockitoSpiesDefiner.createSpyDefinition(wrappedBeanName));
        }
    }

    @Override
    protected String[] generateDefaultLocations(Class<?> clazz) {
        this.mockedBeans = mockedBeansFinder.findMockedBeans(clazz);
        this.spiedBeans = mockedBeansFinder.findSpiedBeans(clazz);

        return super.generateDefaultLocations(clazz);

    }

    @Override
    protected String[] modifyLocations(Class<?> clazz, String... locations) {
        this.mockedBeans = mockedBeansFinder.findMockedBeans(clazz);
        this.spiedBeans = mockedBeansFinder.findSpiedBeans(clazz);

        return super.modifyLocations(clazz, locations);
    }
}

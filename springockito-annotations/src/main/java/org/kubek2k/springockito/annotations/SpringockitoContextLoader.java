package org.kubek2k.springockito.annotations;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.support.GenericXmlContextLoader;

public class SpringockitoContextLoader extends GenericXmlContextLoader {

    private Map<String, Class<?>> mockedBeans = new HashMap<String, Class<?>>();
    private Set<String> spiedBeans;

    private DesiredMockitoBeansFinder mockedBeansFinder = new DesiredMockitoBeansFinder();

    private MockitoBeansDefiner mockitoBeansDefiner = new MockitoBeansDefiner();
    private MockitoSpiesDefiner mockitoSpiesDefiner = new MockitoSpiesDefiner();

    @Override
    protected void customizeContext(GenericApplicationContext context) {
        super.customizeContext(context);
        registerMocks(context, mockedBeans);
        registerSpies(context, spiedBeans);
    }

    private void registerMocks(GenericApplicationContext context, Map<String, Class<?>> mockedBeans) {
        for (Map.Entry<String, Class<?>> beanEntry : this.mockedBeans.entrySet()) {
            context.registerBeanDefinition(beanEntry.getKey(),
                    mockitoBeansDefiner.createMockFactoryBeanDefinition(beanEntry.getValue()));
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

    void setMockedBeansFinder(DesiredMockitoBeansFinder mockedBeansFinder) {
        this.mockedBeansFinder = mockedBeansFinder;
    }

    void setMockitoBeansDefiner(MockitoBeansDefiner mockitoBeansDefiner) {
        this.mockitoBeansDefiner = mockitoBeansDefiner;
    }
    
    void setMockitoSpiesDefiner(MockitoSpiesDefiner mockitoSpiesDefiner) {
        this.mockitoSpiesDefiner = mockitoSpiesDefiner;
    }
}

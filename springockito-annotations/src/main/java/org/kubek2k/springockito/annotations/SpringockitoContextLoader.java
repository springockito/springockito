package org.kubek2k.springockito.annotations;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.logging.Logger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextLoader;
import org.springframework.test.context.MergedContextConfiguration;
import org.springframework.test.context.SmartContextLoader;
import org.springframework.test.context.support.AbstractGenericContextLoader;
import org.springframework.test.context.support.DelegatingSmartContextLoader;
import org.springframework.test.context.support.GenericXmlContextLoader;

public class SpringockitoContextLoader implements ContextLoader, SmartContextLoader {

    private static final Logger LOGGER = Logger.getLogger(SpringockitoContextLoader.class.getName());

    private Map<String, DesiredMockitoBeansFinder.MockProperties<ReplaceWithMock>> mockedBeans
            = new HashMap<String, DesiredMockitoBeansFinder.MockProperties<ReplaceWithMock>>();
    private Set<String> spiedBeans;

    private DesiredMockitoBeansFinder mockedBeansFinder = new DesiredMockitoBeansFinder();

    private MockitoBeansDefiner mockitoBeansDefiner = new MockitoBeansDefiner();
    private MockitoSpiesDefiner mockitoSpiesDefiner = new MockitoSpiesDefiner();

    private AbstractGenericContextLoader wrappedLoader;

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

    private void findMockedAndSpiedBeansLocations(Class<?> clazz) {
        this.mockedBeans = mockedBeansFinder.findMockedBeans(clazz);
        this.spiedBeans = mockedBeansFinder.findSpiedBeans(clazz);
    }

    /**
     * Invoked by Spring < 3.1
     * @param clazz
     * @param locations
     * @return
     */
    public String[] processLocations(Class<?> clazz, String... locations) {
        findMockedAndSpiedBeansLocations(clazz);
        initWrappedContextLoader(clazz);
        return wrappedLoader.processLocations(clazz, locations);
    }

    /**
     * Invoked by Spring >= 3.1
     * @param configAttributes
     */
    public void processContextConfiguration(ContextConfigurationAttributes configAttributes) {
        findMockedAndSpiedBeansLocations(configAttributes.getDeclaringClass());
        initWrappedContextLoader(configAttributes.getDeclaringClass());
        wrappedLoader.processContextConfiguration(configAttributes);
    }

    private void initWrappedContextLoader(Class<?> clazz) {
        if (this.wrappedLoader == null) {
            try {
                LoadContextWith loadContextWith = clazz.getAnnotation(LoadContextWith.class);
                if (loadContextWith != null && loadContextWith.value() != null) {
                    this.wrappedLoader = loadContextWith.value().newInstance();
                } else {
                    this.wrappedLoader = new GenericXmlContextLoader();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            LOGGER.warning("The init of re-init wrapped context loader invoked - please check your inheritance hierarchy");
        }
    }

    /**
     * Invoked by Spring < 3.1
     * @param locations
     * @return
     * @throws Exception
     */
    public ApplicationContext loadContext(String... locations) throws Exception {
        // we can cast here, the instance is returned as GenericApplicationContext {@link AbstractGenericContextLoader}
        GenericApplicationContext context = (GenericApplicationContext) wrappedLoader.loadContext(locations);

        registerMocksAndSpies(context);

        context.refresh();

        return context;
    }

    /**
     * Invoked by Spring >= 3.1
     * @param mergedConfig
     * @return
     * @throws Exception
     */
    public ApplicationContext loadContext(MergedContextConfiguration mergedConfig) throws Exception {
        GenericApplicationContext context = (GenericApplicationContext) wrappedLoader.loadContext(mergedConfig);

        registerMocksAndSpies(context);

        return context;
    }

    private void registerMocksAndSpies(GenericApplicationContext context) {
        registerMocks(context, this.mockedBeans);
        registerSpies(context, this.spiedBeans);
    }
}

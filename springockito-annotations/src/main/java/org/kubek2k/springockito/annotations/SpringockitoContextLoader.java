package org.kubek2k.springockito.annotations;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.support.GenericXmlContextLoader;

import java.util.*;

public class SpringockitoContextLoader extends GenericXmlContextLoader {

    /**
     * Have to be sorted since we want a nice caching of customized application contexts
     */
    private Map<String, DesiredMockitoBeansFinder.MockProperties<ReplaceWithMock>> mockedBeans
            = new TreeMap<String, DesiredMockitoBeansFinder.MockProperties<ReplaceWithMock>>();
    private Set<String> spiedBeans = new TreeSet<String>();

    private DesiredMockitoBeansFinder mockedBeansFinder = new DesiredMockitoBeansFinder();

    private MockitoBeansDefiner mockitoBeansDefiner = new MockitoBeansDefiner();
    private MockitoSpiesDefiner mockitoSpiesDefiner = new MockitoSpiesDefiner();

    @Override
    protected void customizeContext(GenericApplicationContext context) {
        super.customizeContext(context);
        registerMocks(context, mockedBeans);
        registerSpies(context, spiedBeans);
    }

    private void registerMocks(GenericApplicationContext context,
                               Map<String, DesiredMockitoBeansFinder.MockProperties<ReplaceWithMock>> mockedBeans) {
        for (Map.Entry<String, DesiredMockitoBeansFinder.MockProperties<ReplaceWithMock>> beanEntry : mockedBeans.entrySet()) {
            DesiredMockitoBeansFinder.MockProperties<ReplaceWithMock> mockProperties = beanEntry.getValue();
            ReplaceWithMock replaceWithMockAnnotation = mockProperties.getAnnotationInstance();
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

    private void defineMocksAndSpies(Class<?> clazz) {
        this.mockedBeans.putAll(mockedBeansFinder.findMockedBeans(clazz));
        this.spiedBeans.addAll(mockedBeansFinder.findSpiedBeans(clazz));
    }

    private List<String> generateLocationForMocksAndSpies() {
        List<String> result = new ArrayList<String>(mockedBeans.size() + spiedBeans.size());

        for (Map.Entry<String, DesiredMockitoBeansFinder.MockProperties<ReplaceWithMock>> mockDefinitionEntry : mockedBeans.entrySet()) {
            result.add("classpath*:/mock-" + mockDefinitionEntry.getKey() + "=" + mockDefinitionEntry.getValue().getClasspathRepresentation());
        }

        for (String spiedBean : spiedBeans) {
            result.add("classpath*:/spy-" + spiedBean);
        }

        return result;
    }

    // I know I could use commons or sth but I don't want to introduce more deps than are actually really really needed
    private <T> List<T> concat(List<T> list1, List<T> list2) {
        List<T> result = new ArrayList<T>(list1.size() + list2.size());

        result.addAll(list1);
        result.addAll(list2);

        return result;
    }

    private String[] addFakeLocationsOfBeansAndSpies(String[] locations) {
        List<String> locationOfMocksAndSpies = generateLocationForMocksAndSpies();
        return concat(Arrays.asList(locations), locationOfMocksAndSpies)
                .toArray(new String[locations.length + locationOfMocksAndSpies.size()]);
    }

    @Override
    protected String[] generateDefaultLocations(Class<?> clazz) {
        String[] resultingLocations = super.generateDefaultLocations(clazz);

        defineMocksAndSpies(clazz);

        return addFakeLocationsOfBeansAndSpies(resultingLocations);

    }

    @Override
    protected String[] modifyLocations(Class<?> clazz, String... passedLocations) {
        String[] resultingLocations = super.modifyLocations(clazz, passedLocations);

        defineMocksAndSpies(clazz);

        return addFakeLocationsOfBeansAndSpies(resultingLocations);
    }
}

package org.kubek2k.springockito.annotations;

import org.kubek2k.springockito.annotations.internal.Loader;
import org.springframework.test.context.web.GenericXmlWebContextLoader;
import org.springframework.test.context.web.WebMergedContextConfiguration;
import org.springframework.web.context.support.GenericWebApplicationContext;

public class SpringockitoWebXmlContextLoader extends GenericXmlWebContextLoader {

    private Loader loader = new Loader();

    @Override
    protected void customizeContext(GenericWebApplicationContext context,final WebMergedContextConfiguration webMergedConfig) {
        super.customizeContext(context,webMergedConfig);
        loader.registerMocksAndSpies(context);
    }

    @Override
    protected String[] generateDefaultLocations(Class<?> clazz) {
        String[] resultingLocations = super.generateDefaultLocations(clazz);
        loader.defineMocksAndSpies(clazz);
        return resultingLocations;
    }

    @Override
    protected String[] modifyLocations(Class<?> clazz, String... passedLocations) {
        String[] resultingLocations = super.modifyLocations(clazz, passedLocations);
        loader.defineMocksAndSpies(clazz);
        return resultingLocations;
    }

}

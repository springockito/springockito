package org.kubek2k.springockito.annotations;

import org.kubek2k.springockito.annotations.internal.Loader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.support.GenericXmlContextLoader;

public class SpringockitoContextLoader extends GenericXmlContextLoader {

    private Loader loader = new Loader();

    @Override
    protected void customizeContext(GenericApplicationContext context) {
        super.customizeContext(context);
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

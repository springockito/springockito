package org.kubek2k.springockito.annotations;

import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.context.support.DelegatingSmartContextLoader;

public class SpringockitoSmartContextLoader extends DelegatingSmartContextLoader {

    @Override
    public void processContextConfiguration(ContextConfigurationAttributes configAttributes) {
    }
}

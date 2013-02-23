package org.kubek2k.springockito.annotations.internal;

import org.kubek2k.springockito.annotations.internal.definitions.SpringockitoDefinition;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DefinitionRegistry {

    private Map<String, SpringockitoDefinition> springockitoDefinitionMap = new HashMap<String, SpringockitoDefinition>();

    public void registerAll(Set<SpringockitoDefinition> springockitoDefinitions) {
        for (SpringockitoDefinition springockitoDefinition : springockitoDefinitions) {
            //TODO: log when overriding
            springockitoDefinitionMap.put(springockitoDefinition.getTargetBeanName(), springockitoDefinition);
        }
    }

    public Iterable<SpringockitoDefinition> getRegistered() {
        return springockitoDefinitionMap.values();
    }
}

package org.kubek2k.springockito.annotations.internal.naming.strategies;

import javax.annotation.Resource;
import java.lang.reflect.Field;

public class ResourceBeanNameResolver extends AbstractBeanNameResolver {

    @Override
    protected String resolveBeanName(Field field) {
        Resource resource = field.getAnnotation(Resource.class);
        if (resource == null) {
            return null;
        }
        return resource.name();
    }

}

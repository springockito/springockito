package org.kubek2k.springockito.annotations.internal.naming;

import java.lang.reflect.Field;

public interface BeanNameResolver {

    String retrieveBeanName(Field field);

}

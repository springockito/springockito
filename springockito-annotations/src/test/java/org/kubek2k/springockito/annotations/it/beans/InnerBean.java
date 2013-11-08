package org.kubek2k.springockito.annotations.it.beans;

public class InnerBean {

    public static final int VALUE_RETURNED_BY_INNER = 300;

    public int doSomething() {
        return VALUE_RETURNED_BY_INNER;
    }

    public Runnable methodReturningInteger() {
        return null;
    }

}

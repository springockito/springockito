package org.kubek2k.springockito.jira025;

public class SomeBean {

    private SomeServiceInterface someServiceInterface;

    public void setSomeServiceInterface(SomeServiceInterface someServiceInterface) {
        this.someServiceInterface = someServiceInterface;
    }

    public SomeServiceInterface getSomeServiceInterface() {
        return someServiceInterface;
    }
}

package org.kubek2k.springockito.jira011.mock;

public class BeanInjectedWith {

    private FirstBeanNotInContext firstBeanNotInContext;
    private SecondBeanNotInContext secondBeanNotInContext;

    public FirstBeanNotInContext getFirstBeanNotInContext() {
        return firstBeanNotInContext;
    }

    public void setFirstBeanNotInContext(FirstBeanNotInContext firstBeanNotInContext) {
        this.firstBeanNotInContext = firstBeanNotInContext;
    }

    public SecondBeanNotInContext getSecondBeanNotInContext() {
        return secondBeanNotInContext;
    }

    public void setSecondBeanNotInContext(SecondBeanNotInContext secondBeanNotInContext) {
        this.secondBeanNotInContext = secondBeanNotInContext;
    }
}

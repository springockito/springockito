package org.kubek2k.springockito.annotations;

public class OuterBean {
    private InnerBean innerBean;

    public void setInnerBean(InnerBean innerBean) {
        this.innerBean = innerBean;
    }
    
    public int doSomething() {
        return innerBean.doSomething();
    }
}

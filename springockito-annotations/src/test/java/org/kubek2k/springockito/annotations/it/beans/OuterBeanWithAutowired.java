package org.kubek2k.springockito.annotations.it.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OuterBeanWithAutowired {

    @Autowired
    private InnerBean innerBean;

    public void setInnerBean(InnerBean innerBean) {
        this.innerBean = innerBean;
    }

    public int doSomething() {
        return innerBean.doSomething();
    }
}

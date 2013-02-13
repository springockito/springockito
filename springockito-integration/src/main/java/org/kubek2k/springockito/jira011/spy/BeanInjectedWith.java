package org.kubek2k.springockito.jira011.spy;

public class BeanInjectedWith {

    private FirstSpiedBean firstSpiedBean;
    private SecondSpiedBean secondSpiedBean;

    public FirstSpiedBean getFirstSpiedBean() {
        return firstSpiedBean;
    }

    public void setFirstSpiedBean(FirstSpiedBean firstSpiedBean) {
        this.firstSpiedBean = firstSpiedBean;
    }

    public SecondSpiedBean getSecondSpiedBean() {
        return secondSpiedBean;
    }

    public void setSecondSpiedBean(SecondSpiedBean secondSpiedBean) {
        this.secondSpiedBean = secondSpiedBean;
    }
}

package org.kubek2k.springockito.general.contextcaching;

public class BeanTestingContextCaching {
    private int state;

    public void incrementState() {
        state++;
    }

    public int getState() {
        return state;
    }
}

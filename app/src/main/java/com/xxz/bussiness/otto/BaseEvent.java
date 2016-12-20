package com.xxz.bussiness.otto;

/**
 * Created by wallace on 14/12/29.
 */
public class BaseEvent {
    private boolean isHandled;

    public boolean isHandled() {
        return isHandled;
    }

    public void setHandled() {
        this.isHandled = true;
    }
}

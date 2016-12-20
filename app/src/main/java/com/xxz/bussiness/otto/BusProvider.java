package com.xxz.bussiness.otto;

import com.squareup.otto.Bus;
import com.xxz.application.VideoApplication;

public final class BusProvider {

    private static final Bus BUS = new Bus();

    private BusProvider() {
        // No instances.
    }

    /**
     * 全部切换到主线程发送
     *
     * @param event
     */
    public static void post(final BaseEvent event) {
        if (event == null) return;
        VideoApplication.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                BUS.post(event);
            }
        });
    }

    public static void register(final Object object) {
        if (object == null) return;
        BUS.register(object);
    }

    public static void unregister(final Object object) {
        if (object == null) return;
        BUS.unregister(object);
    }
}
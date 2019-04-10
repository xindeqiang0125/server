package com.xcs.server.opc.memory;

public class SubscriberHolder {
    private DataSubscriber subscriber;
    private int pushInterval;

    public SubscriberHolder(DataSubscriber subscriber, int pushInterval) {
        this.subscriber = subscriber;
        this.pushInterval = pushInterval;
    }

    public DataSubscriber getSubscriber() {
        return subscriber;
    }

    public int getPushInterval() {
        return pushInterval;
    }
}

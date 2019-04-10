package com.xcs.server.opc.memory;

public interface DataSubscriber {
    Strategy getStrategy();
    void onReceived(ValueMap datas);

    enum Strategy {
        ALL, CHANGED, REQUIRED
    }
}

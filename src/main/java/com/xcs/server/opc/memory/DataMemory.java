package com.xcs.server.opc.memory;

import com.xcs.server.opc.Value;

public interface DataMemory {
    void put(String key, Value value);

    Value get(String key);

    void addSubscriber(DataSubscriber subscriber,int pushInterval);

    void removeSubscriber(DataSubscriber subscriber);
}

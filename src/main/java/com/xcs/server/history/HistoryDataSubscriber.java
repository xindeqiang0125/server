package com.xcs.server.history;

import com.xcs.server.opc.memory.DataSubscriber;
import com.xcs.server.opc.memory.ValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class HistoryDataSubscriber implements DataSubscriber {

    @Autowired
    History history;

    @Override
    public Strategy getStrategy() {
        return Strategy.CHANGED;
    }

    @Override
    public void onReceived(ValueMap datas) {
        history.saveHistoryDatas(datas, LocalDateTime.now());
    }
}

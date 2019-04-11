package com.xcs.server.history.impl;

import com.xcs.server.history.History;
import com.xcs.server.opc.memory.ValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Primary
public class HistoryFacade implements History {

    @Autowired
    HdfsHistory hdfsHistory;

    @Autowired
    MySqlHistory mySqlHistory;

    @Override
    public List getDataHistory(Integer itemId, LocalDateTime start, LocalDateTime end) {
        return mySqlHistory.getDataHistory(itemId, start, end);
    }

    @Override
    public void saveHistoryDatas(ValueMap datas, LocalDateTime now) {
        mySqlHistory.saveHistoryDatas(datas, now);
        hdfsHistory.saveHistoryDatas(datas, now);
    }
}

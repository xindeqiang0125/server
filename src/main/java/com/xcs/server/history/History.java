package com.xcs.server.history;

import com.xcs.server.opc.memory.ValueMap;

import java.time.LocalDateTime;
import java.util.List;

public interface History {
    List getDataHistory(Integer itemId, LocalDateTime start, LocalDateTime end);
    void saveHistoryDatas(ValueMap datas, LocalDateTime now);
}

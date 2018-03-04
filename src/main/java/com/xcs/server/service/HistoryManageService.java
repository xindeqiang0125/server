package com.xcs.server.service;

import javafish.clients.opc.variant.Variant;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface HistoryManageService {
    List getDataHistory(Integer itemId, LocalDateTime start, LocalDateTime end);
    void saveHistoryDatas(Map<Integer, Variant> datas, LocalDateTime now);
}

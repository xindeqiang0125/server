package com.xcs.server.history.impl;

import com.xcs.server.history.domain.BooleanHistory;
import com.xcs.server.history.domain.DoubleHistory;
import com.xcs.server.history.domain.IntegerHistory;
import com.xcs.server.history.domain.StringHistory;
import com.xcs.server.web.domain.opc.XItem;
import com.xcs.server.history.History;
import com.xcs.server.opc.memory.ValueMap;
import com.xcs.server.web.repository.history.BooleanHistoryRepository;
import com.xcs.server.web.repository.history.DoubleHistoryRepository;
import com.xcs.server.web.repository.history.IntegerHistoryRepository;
import com.xcs.server.web.repository.history.StringHistoryRepository;
import com.xcs.server.web.repository.opc.ItemRepository;
import com.xcs.server.web.service.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MySqlHistory implements History {
    @Autowired
    private IntegerHistoryRepository integerHistoryRepository;
    @Autowired
    private DoubleHistoryRepository doubleHistoryRepository;
    @Autowired
    private StringHistoryRepository stringHistoryRepository;
    @Autowired
    private BooleanHistoryRepository booleanHistoryRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private ItemTypeService itemTypeService;

    @Override
    public List getDataHistory(Integer itemId, LocalDateTime start, LocalDateTime end) {
        List list = new ArrayList();
        Object element;
        switch (itemTypeService.getItemType(itemId)) {
            case "DOUBLE":
                list = doubleHistoryRepository.findByXItem_IdAndTimeBetween(itemId, start, end);
                element = doubleHistoryRepository.findFirstByXItem_IdAndTimeBeforeOrderByTimeDesc(itemId, start);
                if (element != null) list.add(0, element);
                break;
            case "INTEGER":
                list = integerHistoryRepository.findByXItem_IdAndTimeBetween(itemId, start, end);
                element = integerHistoryRepository.findFirstByXItem_IdAndTimeBeforeOrderByTimeDesc(itemId, start);
                if (element != null) list.add(0, element);
                break;
            case "STRING":
                list = stringHistoryRepository.findByXItem_IdAndTimeBetween(itemId, start, end);
                element = stringHistoryRepository.findFirstByXItem_IdAndTimeBeforeOrderByTimeDesc(itemId, start);
                if (element != null) list.add(0, element);
                break;
            case "BOOLEAN":
                list = booleanHistoryRepository.findByXItem_IdAndTimeBetween(itemId, start, end);
                element = booleanHistoryRepository.findFirstByXItem_IdAndTimeBeforeOrderByTimeDesc(itemId, start);
                if (element != null) list.add(0, element);
                break;
            default:
                break;
        }
        return list;
    }

    @Override
    public void saveHistoryDatas(ValueMap datas, LocalDateTime now) {
        datas.forEach((s, value) -> {
            XItem xItem = itemRepository.findOne(Integer.valueOf(s));
            switch (value.getType()) {
                case "DOUBLE":
                    doubleHistoryRepository.save(new DoubleHistory(value.doubleValue(), now, xItem));
                    break;
                case "INTEGER":
                        integerHistoryRepository.save(new IntegerHistory(value.integerValue(), now, xItem));
                    break;
                case "STRING":
                    stringHistoryRepository.save(new StringHistory(value.stringValue(), now, xItem));
                    break;
                case "BOOLEAN":
                    booleanHistoryRepository.save(new BooleanHistory(value.booleanValue(), now, xItem));
                    break;
                default:
                    break;
            }
        });
    }

    @Override
    public void setUp() {

    }
}

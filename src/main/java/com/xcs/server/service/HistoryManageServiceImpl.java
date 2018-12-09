package com.xcs.server.service;

import com.xcs.server.domain.BooleanHistory;
import com.xcs.server.domain.DoubleHistory;
import com.xcs.server.domain.IntegerHistory;
import com.xcs.server.domain.StringHistory;
import com.xcs.server.domain.opc.XItem;
import com.xcs.server.repository.BooleanHistoryRepository;
import com.xcs.server.repository.DoubleHistoryRepository;
import com.xcs.server.repository.IntegerHistoryRepository;
import com.xcs.server.repository.StringHistoryRepository;
import com.xcs.server.repository.opc.ItemRepository;
import javafish.clients.opc.variant.Variant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class HistoryManageServiceImpl implements HistoryManageService {
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

    @Override
    public List getDataHistory(Integer itemId, LocalDateTime start, LocalDateTime end) {
        List list = new ArrayList();
        Object element;
        switch (itemRepository.findOne(itemId).getType().toUpperCase()) {
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
    public void saveHistoryDatas(Map<Integer, Variant> datas, LocalDateTime now) {
        for (Map.Entry<Integer, Variant> entry : datas.entrySet()) {
            Integer id = entry.getKey();
            Variant data = entry.getValue();
            XItem xItem = itemRepository.findOne(id);
            switch (xItem.getType().toUpperCase()) {
                case "DOUBLE":
                    doubleHistoryRepository.save(new DoubleHistory(data.getDouble(), now, xItem));
                    break;
                case "INTEGER":
                    if (data.getVariantType() == 2)
                        integerHistoryRepository.save(new IntegerHistory((int) data.getWord(), now, xItem));
                    else integerHistoryRepository.save(new IntegerHistory(data.getInteger(), now, xItem));
                    break;
                case "STRING":
                    stringHistoryRepository.save(new StringHistory(data.getString(), now, xItem));
                    break;
                case "BOOLEAN":
                    booleanHistoryRepository.save(new BooleanHistory(data.getBoolean(), now, xItem));
                    break;
                default:
                    break;
            }
        }
    }
}

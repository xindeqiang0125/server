package com.xcs.server.opc.memory.impl;

import com.xcs.server.opc.data.Value;
import com.xcs.server.opc.memory.ValueMap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@ConditionalOnProperty(name = "data.memory.strategy" , havingValue = "memory" , matchIfMissing = true)
public class MapDataMemory extends AbstractDataMemory {
    private ValueMap datas = new ValueMap();
    @Override
    public void put(String key, Value value) {
        datas.put(key, value);
        super.put(key, value);
    }

    @Override
    public Value get(String key) {
        return datas.get(key);
    }

    @Override
    protected ValueMap getAllDatas() {
        return datas;
    }

    @Override
    protected synchronized ValueMap getChangedDatas() {
        ValueMap map = new ValueMap();
        for (String changedPoint : changedPoints) {
            map.put(changedPoint,datas.get(changedPoint));
        }
        changedPoints.clear();
        return map;
    }

    @Override
    protected ValueMap getRequiredDatas(Set<String> points) {
        ValueMap map = new ValueMap();
        for (String point : points) {
            map.put(point,datas.get(point));
        }
        return map;
    }
}

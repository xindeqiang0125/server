package com.xcs.server.opc.memory.impl;

import com.xcs.server.opc.Value;
import com.xcs.server.opc.memory.ValueMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(name = "data.memory.strategy", havingValue = "redis")
public class RedisDataMemory extends AbstractDataMemory {

    @Autowired
    RedisTemplate<String, Value> redisTemplate;

    @Override
    protected ValueMap getAllDatas() {
        Set<String> keys = redisTemplate.keys("ItemValue:*");
        return getDataMap(keys);
    }

    @Override
    protected synchronized ValueMap getChangedDatas() {
        ValueMap dataMap = getDataMap(toRedisKeys(changedPoints));
        changedPoints.clear();
        return dataMap;
    }

    @Override
    protected ValueMap getRequiredDatas(Set<String> points) {
        return getDataMap(toRedisKeys(points));
    }

    private Set<String> toRedisKeys(Set<String> points) {
        Set<String> collect = points.stream().map(new Function<String, String>() {
            @Override
            public String apply(String s) {
                return "ItemValue:" + s;
            }
        }).collect(Collectors.toSet());
        return collect;
    }

    private ValueMap getDataMap(Collection<String> points) {
//        List<String> strings = new ArrayList<>();
//        for (String point : points) {
//            strings.add(point);
//        }
        List<Value> variants = redisTemplate.opsForValue().multiGet(points);
        ValueMap map = new ValueMap();
        int i = 0;
        for (String point : points) {
            map.put(point.substring(point.indexOf(":") + 1), variants.get(i++));
        }
        return map;
    }

    @Override
    public Value get(String key) {
        return redisTemplate.opsForValue().get("ItemValue:" + key);
    }

    @Override
    public void put(String key, Value value) {
        super.put(key, value);
        redisTemplate.opsForValue().set("ItemValue:" + key, value);
    }
}

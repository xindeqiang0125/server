package com.xcs.server.service;

import com.xcs.server.repository.opc.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ItemTypeService {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public String getItemType(Integer itemId){
        String res = stringRedisTemplate.opsForValue().get("ItemType:" + itemId);
        if (!StringUtils.isEmpty(res)) return res;
        String type = itemRepository.findOne(itemId).getType();
        stringRedisTemplate.opsForValue().set("ItemType:" + itemId,type);
        return type;
    }

//    private Value.Type type(String res) {
//        String upperCase = res.toUpperCase();
//        if (upperCase.equals("DOUBLE")) return Value.Type.DOUBLE;
//        else if (upperCase.equals("INTEGER")) return Value.Type.INTEGER;
//        else if (upperCase.equals("STRING")) return Value.Type.STRING;
//        else if (upperCase.equals("BOOLEAN")) return Value.Type.BOOLEAN;
//        else return Value.Type.STRING;
//    }
}

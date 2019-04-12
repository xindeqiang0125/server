package com.xcs.server.web.service.impl;

import com.xcs.server.web.repository.opc.ItemRepository;
import com.xcs.server.web.service.ItemTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ItemTypeServiceImpl implements ItemTypeService {
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

}

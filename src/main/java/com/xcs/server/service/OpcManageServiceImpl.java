package com.xcs.server.service;

import com.xcs.server.domain.opc.XGroup;
import com.xcs.server.domain.opc.XItem;
import com.xcs.server.domain.opc.XOpc;
import com.xcs.server.repository.opc.GroupRepository;
import com.xcs.server.repository.opc.ItemRepository;
import com.xcs.server.repository.opc.OpcRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OpcManageServiceImpl implements OpcManageService {
    @Autowired
    private OpcRepository opcRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void saveOpcs(List<XOpc> xOpcs) {
        opcRepository.save(xOpcs);
    }

    @Override
    public void deleteOpcs(List<Integer> xOpcIds) {
        for (Integer xOpcId : xOpcIds)
            opcRepository.delete(xOpcId);
    }

    @Override
    public Page<XOpc> getOpcs(Pageable pageable) {
        return opcRepository.findAll(pageable);
    }

    @Override
    public void saveGroups(List<XGroup> xGroups) {
        groupRepository.save(xGroups);
    }

    @Override
    public void deleteGroups(List<Integer> xGroupIds) {
        for (Integer xGroupId : xGroupIds)
            groupRepository.delete(xGroupId);
    }

    @Override
    public Page<XGroup> getGroups(Integer opcId, String groupName, Pageable pageable) {
        return groupRepository.findByOpc_IdAndGroupNameContaining(opcId, groupName, pageable);
    }

    @Override
    public List<XGroup> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Page<XGroup> getAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable);
    }


    @Override
    public void saveItems(List<XItem> xItems) {
        itemRepository.save(xItems);
    }

    @Override
    public void deleteItems(List<Integer> xItemIds) {
        for (Integer xItemId : xItemIds)
            itemRepository.delete(xItemId);
    }

    @Override
    public Page<XItem> getItems(Integer groupId, String itemName, Pageable pageable) {
        if (groupId == null) {
            return itemRepository.findByItemNameContaining(itemName, pageable);
        }else {
            return itemRepository.findByGroup_IdAndItemNameContaining(groupId, itemName, pageable);
        }
    }

    @Override
    public XItem getItem(Integer itemId) {
        return itemRepository.findOne(itemId);
    }

    @Override
    public void saveItem(XItem xItem) {
        itemRepository.save(xItem);
    }

    @Override
    public void saveGroup(XGroup xGroup) {
        groupRepository.save(xGroup);
    }

    @Override
    public void saveOpc(XOpc xOpc) {
        opcRepository.save(xOpc);
    }

    @Override
    public Page<XItem> getAllItems(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    @Override
    public List<XOpc> getAllOpcs() {
        return opcRepository.findAll();
    }
}

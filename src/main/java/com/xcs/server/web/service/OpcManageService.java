package com.xcs.server.web.service;

import com.xcs.server.web.domain.opc.XGroup;
import com.xcs.server.web.domain.opc.XItem;
import com.xcs.server.web.domain.opc.XOpc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OpcManageService {
    void saveOpcs(List<XOpc> xOpcs);
    void deleteOpcs(List<Integer> xOpcIds);
    Page<XOpc> getOpcs(Pageable pageable);
    List<XOpc> getAllOpcs();

    void saveGroups(List<XGroup> xGroups);
    void deleteGroups(List<Integer> xGroupIds);
    Page<XGroup> getGroups(Integer opcId,String groupName,Pageable pageable);
    List<XGroup> getAllGroups();
    Page<XGroup> getAllGroups(Pageable pageable);

    void saveItems(List<XItem> xItems);
    void deleteItems(List<Integer> xItemIds);
    Page<XItem> getItems(Integer groupId,String itemName,Pageable pageable);
    XItem getItem(Integer itemId);
    Page<XItem> getAllItems(Pageable pageable);

    void saveItem(XItem xItem);

    void saveGroup(XGroup xGroup);

    void saveOpc(XOpc xOpc);
}

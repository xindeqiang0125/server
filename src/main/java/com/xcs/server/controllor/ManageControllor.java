package com.xcs.server.controllor;

import com.xcs.server.domain.opc.XGroup;
import com.xcs.server.domain.opc.XItem;
import com.xcs.server.domain.opc.XOpc;
import com.xcs.server.history.impl.MySqlHistory;
import com.xcs.server.service.OpcManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ManageControllor {
    @Autowired
    private OpcManageService opcManageService;
    @Autowired
    private MySqlHistory mySqlHistory;

    @GetMapping("/initopcs")
    public void initDatas(){
        XOpc xOpc = new XOpc("localhost", "Matrikon.OPC.Simulation", "JOPC1");

        XGroup xGroup1=new XGroup("Random",true,1000,0f);
        xGroup1.setOpc(xOpc);
        XGroup xGroup2=new XGroup("Triangle",true,1000,0f);
        xGroup2.setOpc(xOpc);
        XGroup xGroup3=new XGroup("Square",true,1000,0f);
        xGroup3.setOpc(xOpc);

        XItem xItem1=new XItem("Random.Int1",true,"","INTEGER");
        xItem1.setGroup(xGroup1);
        XItem xItem2=new XItem("Random.Int2",true,"","INTEGER");
        xItem2.setGroup(xGroup1);
        XItem xItem3=new XItem("Random.Real4",true,"","DOUBLE");
        xItem3.setGroup(xGroup1);
        XItem xItem4=new XItem("Random.String",true,"","STRING");
        xItem4.setGroup(xGroup1);
        XItem xItem5=new XItem("Random.Time",true,"","STRING");
        xItem5.setGroup(xGroup1);
        XItem xItem6=new XItem("Triangle Waves.Int1",true,"","INTEGER");
        xItem6.setGroup(xGroup2);
        XItem xItem7=new XItem("Triangle Waves.Int2",true,"","INTEGER");
        xItem7.setGroup(xGroup2);
        XItem xItem8=new XItem("Triangle Waves.Int4",true,"","INTEGER");
        xItem8.setGroup(xGroup2);
        XItem xItem9=new XItem("Triangle Waves.Real4",true,"","DOUBLE");
        xItem9.setGroup(xGroup2);
        XItem xItem10=new XItem("Triangle Waves.Real8",true,"","DOUBLE");
        xItem10.setGroup(xGroup2);
        XItem xItem11=new XItem("Square Waves.Boolean",true,"","BOOLEAN");
        xItem11.setGroup(xGroup3);
        XItem xItem12=new XItem("Square Waves.Int1",true,"","INTEGER");
        xItem12.setGroup(xGroup3);
        XItem xItem13=new XItem("Square Waves.Int2",true,"","INTEGER");
        xItem13.setGroup(xGroup3);
        XItem xItem14=new XItem("Square Waves.Int4",true,"","INTEGER");
        xItem14.setGroup(xGroup3);
        XItem xItem15=new XItem("Square Waves.Real8",true,"","DOUBLE");
        xItem15.setGroup(xGroup3);

        opcManageService.saveOpcs(Arrays.asList(xOpc));
        opcManageService.saveGroups(Arrays.asList(xGroup1,xGroup2,xGroup3));
        opcManageService.saveItems(Arrays.asList(
                xItem1,
                xItem2,
                xItem3,
                xItem4,
                xItem5,
                xItem6,
                xItem7,
                xItem8,
                xItem9,
                xItem10,
                xItem11,
                xItem12,
                xItem13,
                xItem14,
                xItem15));
    }

    @PostMapping("/manage/saveopcs")
    public ResponseMsg saveOrUpdateOpcs(@RequestBody List<XOpc> xOpcs){
        try {
            if (xOpcs != null) {
                opcManageService.saveOpcs(xOpcs);
            }
        } catch (Exception e) {
            return new ResponseMsg(ResponseMsg.FAILED,"保存失败！");
        }
        return new ResponseMsg(ResponseMsg.SUCCESS,"保存成功！");
    }

    @PostMapping("/manage/saveopc")
    public ResponseMsg saveOrUpdateOpc(XOpc xOpc){
        try {
            if (xOpc != null) {
                opcManageService.saveOpc(xOpc);
            }
        } catch (Exception e) {
            return new ResponseMsg(ResponseMsg.FAILED,"保存失败！");
        }
        return new ResponseMsg(ResponseMsg.SUCCESS,"保存成功！");
    }

    @PostMapping("/manage/deleteopcs")
    public ResponseMsg deleteOpcs(@RequestBody List<Integer> xOpcIds){
        try {
            if (xOpcIds != null){
                opcManageService.deleteOpcs(xOpcIds);
            }
        } catch (Exception e) {
            return new ResponseMsg(ResponseMsg.FAILED,"删除失败！");
        }
        return new ResponseMsg(ResponseMsg.SUCCESS,"删除成功！");
    }

    @PostMapping("/manage/getopcsbypage")
    public Map<String,Object> getOpcs(Integer page,Integer rows){
        if (page == null) page=1;
        if (rows == null) rows=10;
        Page<XOpc> opcs = opcManageService.getOpcs(new PageRequest(page-1, rows));
        Map<String,Object> res=new HashMap<>();
        res.put("total",opcs.getTotalElements());
        res.put("rows",opcs.getContent());
        return res;
    }

    @PostMapping("/manage/getopcs")
    public List<XOpc> getOpcs(){
        return opcManageService.getAllOpcs();
    }

    @PostMapping("/manage/savegroups")
    public ResponseMsg saveOrUpdateGroups(@RequestBody List<XGroup> xGroups){
        try {
            if (xGroups != null) {
                opcManageService.saveGroups(xGroups);
            }
        } catch (Exception e) {
            return new ResponseMsg(ResponseMsg.FAILED,"保存失败！");
        }
        return new ResponseMsg(ResponseMsg.SUCCESS,"保存成功！");
    }

    @PostMapping("/manage/savegroup")
    public ResponseMsg saveOrUpdateGroup(XGroup xGroup){
        try {
            if (xGroup != null) {
                opcManageService.saveGroup(xGroup);
            }
        } catch (Exception e) {
            return new ResponseMsg(ResponseMsg.FAILED,"保存失败！");
        }
        return new ResponseMsg(ResponseMsg.SUCCESS,"保存成功！");
    }

    @PostMapping("/manage/deletegroups")
    public ResponseMsg deleteGroups(@RequestBody List<Integer> xGroupIds){
        try {
            if (xGroupIds != null){
                opcManageService.deleteGroups(xGroupIds);
            }
        } catch (Exception e) {
            return new ResponseMsg(ResponseMsg.FAILED,"删除失败！");
        }
        return new ResponseMsg(ResponseMsg.SUCCESS,"删除成功！");
    }

    @PostMapping("/manage/getgroupsbypage")
    public Map<String,Object> getGroups(Integer opcId,String groupName,Integer page,Integer rows){
        if (opcId == null) opcId=1;
        if (groupName == null) groupName="";
        if (page == null) page=1;
        if (rows == null) rows=10;
        Page<XGroup> groups = opcManageService.getGroups(opcId,groupName, new PageRequest(page-1, rows));
        Map<String,Object> res=new HashMap<>();
        res.put("total",groups.getTotalElements());
        res.put("rows",groups.getContent());
        return res;
    }

    @PostMapping("/manage/getallgroupsbypage")
    public Map<String,Object> getGroups(Integer page,Integer rows){
        if (page == null) page=1;
        if (rows == null) rows=10;
        Page<XGroup> groups = opcManageService.getAllGroups(new PageRequest(page-1, rows));
        Map<String,Object> res=new HashMap<>();
        res.put("total",groups.getTotalElements());
        res.put("rows",groups.getContent());
        return res;
    }

    @PostMapping("/manage/getgroups")
    public List<XGroup> getGroups(){
        return opcManageService.getAllGroups();
    }

    @PostMapping("/manage/saveitems")
    public ResponseMsg saveOrUpdateItems(@RequestBody List<XItem> xItems){
        try {
            if (xItems != null) {
                opcManageService.saveItems(xItems);
            }
        } catch (Exception e) {
            return new ResponseMsg(ResponseMsg.FAILED,"保存失败！");
        }
        return new ResponseMsg(ResponseMsg.SUCCESS,"保存成功！");
    }

    @PostMapping("/manage/saveitem")
    public ResponseMsg saveOrUpdateItem(XItem xItem){
        try {
            if (xItem != null) {
                opcManageService.saveItem(xItem);
            }
        } catch (Exception e) {
            return new ResponseMsg(ResponseMsg.FAILED,"保存失败！");
        }
        return new ResponseMsg(ResponseMsg.SUCCESS,"保存成功！");
    }

    @PostMapping("/manage/deleteitems")
    public ResponseMsg deleteItems(@RequestBody List<Integer> xItemIds){
        try {
            if (xItemIds != null){
                opcManageService.deleteItems(xItemIds);
            }
        } catch (Exception e) {
            return new ResponseMsg(ResponseMsg.FAILED,"删除失败！");
        }
        return new ResponseMsg(ResponseMsg.SUCCESS,"删除成功！");
    }

    @RequestMapping("/manage/getitemsbypage")
    public Map<String,Object> getItems(Integer groupId,String itemName,Integer page,Integer rows){
        if (itemName == null) itemName="";
        if (page == null) page=1;
        if (rows == null) rows=10;
        Page<XItem> items = opcManageService.getItems(groupId,itemName, new PageRequest(page-1, rows));
        Map<String,Object> res=new HashMap<>();
        res.put("total",items.getTotalElements());
        res.put("rows",items.getContent());
        return res;
    }
    @RequestMapping("/manage/getallitemsbypage")
    public Map<String,Object> getItems(Integer page,Integer rows){
        if (page == null) page=1;
        if (rows == null) rows=10;
        Page<XItem> items = opcManageService.getAllItems(new PageRequest(page-1, rows));
        Map<String,Object> res=new HashMap<>();
        res.put("total",items.getTotalElements());
        res.put("rows",items.getContent());
        return res;
    }

    @RequestMapping("/manage/getitem")
    public XItem getItems(Integer itemId){
        if (itemId == null) itemId=1;
        return opcManageService.getItem(itemId);
    }

    @RequestMapping("/gethistory")
    public List getHistory(Integer itemId,String start,String end){
        if (itemId == null) itemId=1;
        if (start == null) start="2007-12-03T10:15:30";
        if (end == null) end="2020-12-03T10:15:31";
        return mySqlHistory.getDataHistory(itemId, LocalDateTime.parse(start),LocalDateTime.parse(end));
    }
}

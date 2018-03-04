package com.xcs.server.opc.service;

import com.xcs.server.domain.opc.XGroup;
import com.xcs.server.domain.opc.XItem;
import com.xcs.server.domain.opc.XOpc;
import com.xcs.server.opc.data.DataMemory;
import com.xcs.server.service.HistoryManageService;
import com.xcs.server.service.HistoryManageServiceImpl;
import com.xcs.server.service.OpcManageService;
import com.xcs.server.service.OpcManageServiceImpl;
import com.xcs.server.util.SpringUtil;
import javafish.clients.opc.JEasyOpc;
import javafish.clients.opc.asynch.AsynchEvent;
import javafish.clients.opc.asynch.OpcAsynchGroupListener;
import javafish.clients.opc.component.OpcGroup;
import javafish.clients.opc.component.OpcItem;
import javafish.clients.opc.variant.Variant;

import java.time.LocalDateTime;
import java.util.*;

public class OpcClient implements OpcAsynchGroupListener {
    public static boolean running=false;
    private List<JEasyOpc> jEasyOpcs;
    private List<OpcGroup> opcGroups;
    private List<OpcItem> opcItems;
    private Map<String,Integer> itemNameIdMap;
    private HistoryManageService historyManageService;
    private OpcManageService opcManageService;

    public OpcClient() {
        historyManageService = SpringUtil.getApplicationContext().getBean(HistoryManageServiceImpl.class);
        opcManageService = SpringUtil.getApplicationContext().getBean(OpcManageServiceImpl.class);
        jEasyOpcs = new ArrayList<JEasyOpc>();
        opcGroups = new ArrayList<OpcGroup>();
        opcItems = new ArrayList<OpcItem>();
        itemNameIdMap=new HashMap<String,Integer>();
        JEasyOpc.coInitialize();
        init();
    }

    private void init() {
        jEasyOpcs.clear();
        opcGroups.clear();
        opcItems.clear();
        itemNameIdMap.clear();
        List<XOpc> xOpcs = opcManageService.getAllOpcs();
        for (XOpc xOpc : xOpcs) {
            JEasyOpc jEasyOpc = new JEasyOpc(xOpc.getHost(), xOpc.getServerProgID(), xOpc.getServerClientHandle());
            jEasyOpcs.add(jEasyOpc);
            Set<XGroup> xGroups = xOpc.getGroups();
            for (XGroup xGroup : xGroups) {
                OpcGroup opcGroup = new OpcGroup(xGroup.getGroupName(), xGroup.getActive(), xGroup.getUpdateRate(), xGroup.getPercentDeadBand());
                opcGroups.add(opcGroup);
                jEasyOpc.addGroup(opcGroup);
                Set<XItem> xItems = xGroup.getItems();
                for (XItem xItem : xItems) {
                    OpcItem opcItem = new OpcItem(xItem.getItemName(), xItem.getActive(), xItem.getAccessPath());
                    opcItems.add(opcItem);
                    opcGroup.addItem(opcItem);

                    StringBuilder sb=new StringBuilder();
                    sb.append(opcGroup.getGroupName());
                    sb.append(":");
                    sb.append(opcItem.getItemName());
                    itemNameIdMap.put(sb.toString(),xItem.getId());
                }
            }
        }
        for (OpcGroup opcGroup : opcGroups) {
            opcGroup.addAsynchListener(this);
        }
    }

    public void start() {
        this.running=true;
        for (JEasyOpc jEasyOpc : jEasyOpcs) {
            jEasyOpc.start();
        }
    }

    public void stop() {
        this.running=false;
        for (JEasyOpc jEasyOpc : jEasyOpcs) {
            jEasyOpc.terminate();
        }
        JEasyOpc.coUninitialize();
    }

    @Override
    public void getAsynchEvent(AsynchEvent asynchEvent) {
        LocalDateTime now = LocalDateTime.now();
        OpcGroup opcGroup=asynchEvent.getOPCGroup();
        List<OpcItem> opcItems = opcGroup.getItems();
        //需要更新的数据批量存入数据库的临时变量
        Map<Integer,Variant> datas=new HashMap<Integer,Variant>();
        for (OpcItem opcItem:opcItems){
            StringBuilder sb=new StringBuilder();
            sb.append(opcGroup.getGroupName());
            sb.append(":");
            sb.append(opcItem.getItemName());

            Integer itemId=itemNameIdMap.get(sb.toString());
            if (!opcItem.getValue().equals(DataMemory.getDatas().get(itemId))){
                DataMemory.getDatas().put(itemId,opcItem.getValue());
                datas.put(itemId,opcItem.getValue());
            }
        }
        //存入数据库操作
        saveChangedDatasToDatabase(datas,now);
    }

    private void saveChangedDatasToDatabase(Map<Integer, Variant> datas, LocalDateTime now) {
        //System.out.println(datas.size());
        new Thread(new Runnable() {
            @Override
            public void run() {
                historyManageService.saveHistoryDatas(datas,now);
            }
        }).start();
    }

    /**
     * 判断实时数据内存缓冲区的数据是否需要更新（根据opcGroup的死区，opcItem值和缓冲区中现有数据判断）
     * @param opcGroup
     * @param opcItem
     * @param itemId 内存缓冲区opcItem值数据的键
     * @return
     */
    private boolean needToUpdateData(OpcGroup opcGroup,OpcItem opcItem,Integer itemId){
        return false;
    }
}

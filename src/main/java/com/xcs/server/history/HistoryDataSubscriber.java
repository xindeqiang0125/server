package com.xcs.server.history;

import com.xcs.server.opc.memory.DataMemory;
import com.xcs.server.opc.memory.DataSubscriber;
import com.xcs.server.opc.memory.ValueMap;
import com.xcs.server.web.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Component
public class HistoryDataSubscriber implements DataSubscriber {

    @Autowired
    private SettingService settingService;

    @Autowired
    private History history;

    @Autowired
    private DataMemory dataMemory;

    @PostConstruct
    public void setUp(){
        boolean enableSaveData = false;
        try {
            enableSaveData = Boolean.valueOf(settingService.getOne("enable_save_data").getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (enableSaveData){
            int saveDataInterval = Integer.valueOf(settingService.getOne("save_data_interval").getValue());
            dataMemory.addSubscriber(this,saveDataInterval);
        }
    }

    @Override
    public Strategy getStrategy() {
        return Strategy.CHANGED;
    }

    @Override
    public void onReceived(ValueMap datas) {
        history.saveHistoryDatas(datas, LocalDateTime.now());
    }
}

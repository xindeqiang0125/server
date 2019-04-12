package com.xcs.server.opc;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xcs.server.opc.memory.DataMemory;
import com.xcs.server.opc.memory.RequiredDataSubscriber;
import com.xcs.server.opc.memory.ValueMap;
import com.xcs.server.web.service.SettingService;
import com.xcs.server.util.SpringUtil;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@ServerEndpoint("/datas")
public class WsPushDataSubscriber implements RequiredDataSubscriber {
    private DataMemory dataMemory;
    private SettingService settingService;

    @Override
    public Set<String> getRequiredPoints() {
        return itemIds;
    }

    @Override
    public Strategy getStrategy() {
        return Strategy.REQUIRED;
    }

    @Override
    public void onReceived(ValueMap datas) {
        Map<String, String> map = new HashMap<>();
        datas.forEach((s, value) -> map.put(s, value == null ? "null" : value.toString()));
        String json = gson.toJson(map);
        this.session.getAsyncRemote().sendText(json);
    }


    private Session session;

    private Set<String> itemIds;
    private Gson gson = new Gson();
    private int onMessageTimes = 0;

    @OnMessage
    public void onMessage(String message, Session session) {
        if (onMessageTimes++ > 0) return;
        try {
            itemIds = gson.fromJson(message, new TypeToken<HashSet<String>>() {
            }.getType());
            int pushDataInterval= Integer.valueOf(settingService.getOne("push_data_interval").getValue());
            dataMemory.addSubscriber(this, pushDataInterval);
        } catch (Exception e) {

        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        dataMemory = SpringUtil.getApplicationContext().getBean(DataMemory.class);
        settingService = SpringUtil.getApplicationContext().getBean(SettingService.class);
    }

    @OnClose
    public void onClose() {
        dataMemory.removeSubscriber(this);
    }
}

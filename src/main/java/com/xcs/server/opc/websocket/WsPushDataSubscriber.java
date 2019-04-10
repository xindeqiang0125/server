package com.xcs.server.opc.websocket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xcs.server.opc.memory.DataMemory;
import com.xcs.server.opc.memory.RequiredDataSubscriber;
import com.xcs.server.opc.memory.ValueMap;
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

    @OnMessage
    public void onMessage(String message, Session session){
        try {
            itemIds = gson.fromJson(message, new TypeToken<HashSet<String>>() {
            }.getType());
            dataMemory = SpringUtil.getApplicationContext().getBean(DataMemory.class);
            dataMemory.addSubscriber(this, 1000);
        } catch (Exception e) {

        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
    }

    @OnClose
    public void onClose() {
        dataMemory.removeSubscriber(this);
    }
}

package com.xcs.server.opc.websocket;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xcs.server.opc.data.DataMemory;
import javafish.clients.opc.variant.Variant;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@ServerEndpoint("/datas")
public class RealTimeData implements OnSubscribeResponse {
    private Session session;
    private DataMemory dataMemory;
    private List<Integer> itemIds;
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
        try {
            Gson gson=new Gson();
            itemIds=gson.fromJson(message,new TypeToken<ArrayList<Integer>>(){}.getType());
            dataMemory=new DataMemory();
            dataMemory.setOnSubscribeResponse(this);
        }catch (Exception e){

        }
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session=session;
    }

    @OnClose
    public void onClose() {
        dataMemory.stop();
    }

    @Override
    public void subscribeResponse(Map<Integer, Variant> datas) {
        Map<Integer, Variant> response=new HashMap<Integer, Variant>();
        for (Integer itemId:itemIds){
            response.put(itemId,datas.get(itemId));
        }
        this.session.getAsyncRemote().sendText(response.toString());
    }
}

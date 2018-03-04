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
@ServerEndpoint("/websocket")
public class RealTimeData implements OnSubscribeResponse {
    private Session session;
    private DataMemory dataMemory;
    private List<Integer> itemIds;
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {

    }

    @OnOpen
    public void onOpen(Session session) {
        this.session=session;

        String message="[6,7,8,9,10,11,12,13,14,15,1,2,3,4,5]";
        //String message="[]";
        Gson gson=new Gson();
        itemIds=gson.fromJson(message,new TypeToken<ArrayList<Integer>>(){}.getType());

        dataMemory=new DataMemory();
        dataMemory.setOnSubscribeResponse(this);
    }

    @OnClose
    public void onClose() {
        //System.out.println("关闭连接");
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

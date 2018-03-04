package com.xcs.server.opc.data;

import com.xcs.server.opc.ServerSettings;
import com.xcs.server.opc.websocket.OnSubscribeResponse;
import javafish.clients.opc.variant.Variant;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class DataMemory{
    private static HashMap<Integer,Variant> datas;
    private OnSubscribeResponse onSubscribeResponse;
    private Timer timer;

    public DataMemory() {
        timer=new Timer(true);
        timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (onSubscribeResponse != null) {
                            onSubscribeResponse.subscribeResponse(getDatas());
                        }
                    }
                },
                ServerSettings.getSettings().getPushInterval(),
                ServerSettings.getSettings().getPushInterval());
    }

    public static HashMap<Integer, Variant> getDatas() {
        if (datas == null) {
            datas=new HashMap<Integer,Variant>();
        }
        return datas;
    }

    public OnSubscribeResponse getOnSubscribeResponse() {
        return onSubscribeResponse;
    }

    public void setOnSubscribeResponse(OnSubscribeResponse onSubscribeResponse) {
        this.onSubscribeResponse = onSubscribeResponse;
    }

    public void stop() {
        this.timer.cancel();
    }
}

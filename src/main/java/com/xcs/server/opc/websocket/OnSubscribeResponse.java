package com.xcs.server.opc.websocket;

import javafish.clients.opc.variant.Variant;

import java.util.Map;

public interface OnSubscribeResponse {
    public void subscribeResponse(Map<Integer, Variant> datas);
}

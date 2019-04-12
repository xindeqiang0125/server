package com.xcs.server.web.controllor;

import com.xcs.server.opc.OpcClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpcClientControllor {
    private OpcClient client;
    @RequestMapping("/opcClientStart")
    public ResponseMsg opcClientStart(){
        if (OpcClient.running){
            return ResponseMsg.getFailed("OpcClient正在运行!!!无需重复启动!!!");
        }else {
            try {
                client=new OpcClient();
                client.start();
                return ResponseMsg.getSuccess("开启opcClient成功");
            } catch (Exception e) {
                return ResponseMsg.getFailed("开启opcClient失败");
            }
        }
    }
    @RequestMapping("/opcClientStop")
    public ResponseMsg opcClientStop(){
        if (!OpcClient.running){
            return ResponseMsg.getFailed("OpcClient已经关闭!!!无需重复关闭!!!");
        }
        else {
            try {
                client.stop();
                return ResponseMsg.getSuccess("关闭opcClient成功");
            } catch (Exception e) {
                return ResponseMsg.getFailed("关闭opcClient失败");
            }
        }

    }
}

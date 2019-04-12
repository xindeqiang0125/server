package com.xcs.server.web.controllor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CloseController {
    @Autowired
    ApplicationContext applicationContext;
    @RequestMapping("/close")
    public ResponseMsg close(){
        SpringApplication.exit(applicationContext,() -> 0);
        return ResponseMsg.getSuccess("退出成功");
    }
}

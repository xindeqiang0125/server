package com.xcs.server.web.controllor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class CloseController {
    @Autowired
    ApplicationContext applicationContext;
    @Value("${shutdown.grace.path}")
    private String graceShutdowmPath;
    @Value("${shutdown.grace.key}")
    private String graceShutdowmKey;
    @Value("${shutdown.grace.addr}")
    private String graceShutdowmAddr;

    @RequestMapping("/{path}/{key:[^\\.]+}")
    public ResponseMsg close(@PathVariable String path, @PathVariable String key,
                             HttpServletResponse response,
                             HttpServletRequest request) throws IOException {
        if (!request.getRemoteAddr().equals(graceShutdowmAddr)) {
            return ResponseMsg.getFailed("error");
        }
        if (graceShutdowmPath.equals(path)&&graceShutdowmKey.equals(key)) {
            new Thread(() -> SpringApplication.exit(applicationContext,() -> 0)).start();
            return ResponseMsg.getSuccess("退出成功");
        }else {
            return ResponseMsg.getFailed("error");
        }
    }
}

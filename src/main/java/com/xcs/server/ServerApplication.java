package com.xcs.server;

import com.xcs.server.history.HistoryDataSubscriber;
import com.xcs.server.opc.memory.DataMemory;
import com.xcs.server.opc.memory.DataSubscriber;
import com.xcs.server.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
		DataMemory dataMemory = SpringUtil.getApplicationContext().getBean(DataMemory.class);
		DataSubscriber subscriber = SpringUtil.getApplicationContext().getBean(HistoryDataSubscriber.class);
		dataMemory.addSubscriber(subscriber,3000);
	}
}

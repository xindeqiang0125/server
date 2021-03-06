package com.xcs.server.web.config;

import com.xcs.server.web.controllor.interceptor.MainPageInterceptor;
import com.xcs.server.web.controllor.interceptor.ServerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private ServerInterceptor serverInterceptor;
    @Autowired
    private MainPageInterceptor mainPageInterceptor;

    @Value("${shutdown.grace.path}")
    private String graceShutdowmPath;
    @Value("${shutdown.grace.key}")
    private String graceShutdowmKey;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(serverInterceptor).addPathPatterns("/**")
                .excludePathPatterns("/main")
                .excludePathPatterns("/" + graceShutdowmPath + "/" + graceShutdowmKey)
                .excludePathPatterns("/west")
                .excludePathPatterns("/center")
                .excludePathPatterns("/")
                .excludePathPatterns("/no_permission")
                .excludePathPatterns("/login")
                .excludePathPatterns("/applogin")
                .excludePathPatterns("/logout")
                .excludePathPatterns("/error")
                .excludePathPatterns("/center/cs_paint")
                .excludePathPatterns("/manage/getitem")
                .excludePathPatterns("/manage/getitemsbypage")
                .excludePathPatterns("/user/updateuser")
                .excludePathPatterns("/files/all")
                .excludePathPatterns("/files/content")
                .excludePathPatterns("/files/familys")
                .excludePathPatterns("/files/byfamily")
                .excludePathPatterns("/gethistory");
        registry.addInterceptor(mainPageInterceptor).addPathPatterns("/main")
                .addPathPatterns("/west").addPathPatterns("/center");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        super.addViewControllers(registry);
        registry.addViewController("/").setViewName("login");
//        registry.addViewController("/main").setViewName("/main");
        registry.addViewController("/no_permission").setViewName("no_permission");
        registry.addViewController("/center").setViewName("center");
        registry.addViewController("/west").setViewName("west");
        registry.addViewController("/center/manage_opc").setViewName("center/manage_opc");
        registry.addViewController("/center/manage_group").setViewName("center/manage_group");
        registry.addViewController("/center/manage_item").setViewName("center/manage_item");
        registry.addViewController("/center/settings").setViewName("center/settings");
        registry.addViewController("/center/cs_paint").setViewName("center/cs_paint");
        registry.addViewController("/center/user").setViewName("center/user");
        registry.addViewController("/center/user_group").setViewName("center/user_group");
        registry.addViewController("/center/permission").setViewName("center/permission");
        registry.addViewController("/center/file_upload").setViewName("center/file_upload");
        registry.addViewController("/center/manage_file").setViewName("center/manage_file");
    }
}

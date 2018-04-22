package com.xcs.server.controllor.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Set;

@Component
public class ServerInterceptor extends HandlerInterceptorAdapter {
    //long start;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //start=System.currentTimeMillis();
        String requestURI = request.getRequestURI();
        Map<String, Object> user = (Map<String, Object>) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("/no_permission");
            return false;
        }
        Set<String> permissionUris= (Set<String>) user.get("permissionUris");
//        String name = (String) user.get("name");
//        if (name.equals("辛德强")) {
//            return true;
//        }
        if (permissionUris.contains(requestURI)) {
            return true;
        }
        else {
            for (String uri : permissionUris) {
                if (uri.startsWith("/files")) return true;
            }
            response.sendRedirect("/no_permission");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        //long end=System.currentTimeMillis();
        //System.out.println(end - start);
    }
}

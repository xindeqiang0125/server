package com.xcs.server.controllor;

import com.xcs.server.domain.user.User;
import com.xcs.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Controller
public class LoginControllor {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public String login(User user, Model model, HttpSession session,HttpServletResponse response) throws IOException {
        Map<String, Object> checkUser=null;
        try {
            checkUser = userService.checkUser(user);
            if (checkUser == null) throw new Exception("未找到用户");
        } catch (Exception e) {
            //e.printStackTrace();
            model.addAttribute("error",e.getMessage());
            return "login";
        }
        session.setAttribute("user",checkUser);
        response.sendRedirect("/main");
        return null;
    }

    @ResponseBody
    @RequestMapping("/applogin")
    public ResponseMsg appLogin(String telephone,String password){
        try {
            User user = new User();
            user.setTel(telephone);
            user.setPassword(password);
            Map<String, Object> map = userService.checkUser(user);
            return ResponseMsg.getSuccess(map);
        } catch (Exception e) {
            return ResponseMsg.getFailed("登录失败");
        }
    }

    @RequestMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        session.removeAttribute("user");
        response.sendRedirect("/");
    }

    @RequestMapping("/main")
    public String mainPage(HttpSession session, Model model){
        model.addAttribute("user",session.getAttribute("user"));
        return "main";
    }
}

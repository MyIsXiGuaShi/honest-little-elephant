package com.cat.controller;

import com.cat.pojo.User;
import com.cat.service.UserService;
import com.cat.util.Label;
import com.cat.util.MyListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class UserController {
    @Resource(name = "userService")
    private UserService userService;

    @PostMapping("/userLogin")
    public String userLogin(User user,HttpSession session){
        System.out.println(user.getUsername());
        String username =(String) session.getAttribute(Label.USERNAME);
        if(user.getUsername().equals(username)){
            return "redirect:initVotelist";
        }else{
            //用户状态，默认在线
            String message = "该用户已在线！";
            //判断用户是否在线
            boolean result = MyListener.isLogin(user.getUsername());
            if(!result){
                //获取用户
                user = userService.login(user);
                //判断用户编号是否非负
                if(user!=null && user.getUid() > 0){
                    session.setAttribute(Label.USERNAME,user.getUsername());
                    session.setAttribute(Label.USER,user);
                    return "redirect:initVotelist";
                }else{
                    message = "用户名或密码错误！";
                }
            }
            session.setAttribute(Label.MESSAGE,message);
            return "login";
        }

    }
    @PostMapping("/regist")
    public String regist(User user,HttpSession session){
        Integer result = userService.regist(user);
        System.out.println("regist====>uid"+user.getUid());
        if(result!=null && result > 0){
            session.setAttribute(Label.USERNAME,user.getUsername());
            session.setAttribute(Label.USER,user);
            session.setAttribute(Label.MESSAGE,"注册成功！");
            return "message";
        }
        session.setAttribute(Label.MESSAGE,"注册失败！");
        return "regist";
    }
    @PostMapping("/ajaxName")
    public void ajaxName(String username, HttpServletResponse response)throws IOException {
        PrintWriter out = response.getWriter();
        boolean result = userService.verify(username);
        out.print(result);
        out.flush();
        out.close();
    }
    @GetMapping("/userExit")
    public String userExit(HttpSession session){
        session.removeAttribute(Label.USERNAME);
        return "login";
    }
}

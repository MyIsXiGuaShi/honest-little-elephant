package com.cat.util;

import com.cat.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 自定义拦截器
 */
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user =(User) request.getSession().getAttribute(Label.USER);
        if(user!=null && !"".equals(user.getUsername()) && !"".equals(user.getUid())){
            return true;
        }
        response.sendRedirect("login.html");
        return false;
    }
}

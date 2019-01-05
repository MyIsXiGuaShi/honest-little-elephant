package com.cat.util;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 自定义监听器
 */
@WebListener
public class MyListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
    private static ServletContext application;
    /**
     * application创建
     * 服务器启动时启动，服务器销毁时销毁
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("==>application创建");
        application = sce.getServletContext();
        application.setAttribute(Label.MAP,new HashMap<String,Object>());
    }

    /**
     * application销毁
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("==>application销毁");
        application.setAttribute(Label.MAP,null);
    }

    /**
     * session的创建
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setMaxInactiveInterval(5*60);
        System.out.println("==>session的创建");
    }

    /**
     * session的销毁
     *
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        Object object = se.getSession().getAttribute(Label.USERNAME);
        if(object !=null && !"".equals(object)){
            Map<String,Object> map=(Map<String, Object>) application.getAttribute(Label.MAP);
            System.out.println(object.toString());
            map.remove(Label.USERNAME);
            application.setAttribute(Label.MAP,map);
        }
    }
    /**
     * session属性添加
     * 执行setAttribute的时候，当某个属性本来不存在于Session中时, 调用这个方法
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        System.out.println("attributeAdded====>属性名："+se.getName());
        //添加用户名到application
        if(Label.USERNAME.equals(se.getName())){
            System.out.println("attributeAdded====>已在线");
            Map<String,Object> map=(Map<String, Object>) application.getAttribute(Label.MAP);
            map.put(se.getValue().toString(),se.getValue());
            application.setAttribute(Label.MAP,map);
        }

    }
    /**
     * session属性删除
     * 当执行removeAttribute时调用的方法
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {
        if(Label.USERNAME.equals(se.getName())){
            Map<String,Object> map=(Map<String, Object>) application.getAttribute(Label.MAP);
            map.remove(se.getValue().toString());
            System.out.println("attributeRemoved====>已离线");
            application.setAttribute(Label.MAP,map);
        }
    }
    /**
     * session替换
     * 当执行setAttribute时 ,如果某个属性已经存在, 覆盖属性的时候, 调用这个方法
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {
        System.out.println("attributeReplaced====>属性名："+se.getName());
        if(Label.USERNAME.equals(se.getName())){
            System.out.println("attributeReplaced==>value:"+se.getValue());
            Map<String,Object> map=(Map<String, Object>) application.getAttribute(Label.MAP);
            map.put(se.getValue().toString(),se.getValue());
            System.out.println("attributeReplaced====>已替换");
            application.setAttribute(Label.MAP,map);
        }
    }

    /**
     * 判断用户是否在线
     * @param username 用户名
     * @return 执行结果
     */
    public static boolean isLogin(String username){
        System.out.println("isLogin=====>username:"+username);
        Map<String,Object> map=(Map<String, Object>) application.getAttribute(Label.MAP);
        Set<String> keys = map.keySet();
        boolean result = false;
        for(String key : keys){
            if(username.equals(key)){
                result = true;
                break;
            }
        }
        System.out.println("isLogin<===="+result);
        return result;
    }
}

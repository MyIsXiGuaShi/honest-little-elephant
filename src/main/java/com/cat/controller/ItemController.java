package com.cat.controller;

import com.cat.pojo.Item;
import com.cat.service.ItemService;
import com.cat.util.Label;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.*;


@Controller
public class ItemController {
    @Resource(name = "itemService")
    private ItemService itemService;

    @PostMapping("/vote")
    public String addItem(int sid, int uid, @RequestParam Integer[] oid, HttpSession session){
        System.out.println("addItem====>sid:"+sid);
        System.out.println("addItem====>uid:"+uid);
        List<Item> items = new ArrayList<>();
        for(int i =0;i<oid.length;i++){
            items.add(new Item(uid,sid,oid[i]));
        }
        boolean resultA = itemService.selectItem(sid,uid);
        if(!resultA){
            try{
                boolean resultB = itemService.addItem(items);
                if(resultB){
                    session.setAttribute(Label.MESSAGE,"投票成功！");
                    return "message";
                }else{
                    session.setAttribute(Label.MESSAGE,"投票失败！");
                }
            }catch (SQLException e){
                session.setAttribute(Label.ERROR,"投票异常！");
                e.printStackTrace();
                return "error";
            }
        }else{
            session.setAttribute(Label.ERROR,"该用户已经投票，不能重复投票！");
            return "error";
        }
       return "redirect:initVotelist";
    }
}

package com.cat.pojo;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Item {
    //投票编号
    private int iid;
    //用户编号
    private int uid;
    //主题编号
    private int sid;
    //选项编号
    private int oid;
    public Item(){}
    public Item(int uid,int sid,int oid){
        this.uid = uid;
        this.oid = oid;
        this.sid = sid;
    }
}

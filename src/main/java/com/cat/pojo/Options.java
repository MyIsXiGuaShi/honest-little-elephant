package com.cat.pojo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Options {
    //选项编号
    private int oid;
    //选项内容
    private String content;
    //主题编号
    private int osid;
    //票数
    private int opoll;

    public Options(){}
    public Options(int oid){
        this.oid = oid;
    }
    public Options(String content){
        this.content=content;
    }
    public Options(int oid,String content){
        this.oid = oid;
        this.content = content;
    }
    public Options(String content,int osid){
        this.content = content;
        this.osid = osid;
    }
}

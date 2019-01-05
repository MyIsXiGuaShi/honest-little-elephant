package com.cat.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Subject {
    //主题编号
    private int sid;
    //标题
    private String title;
    //类型
    private String type;
    //选项数
    private int optionNum;
    //人数
    private int peopleNum;
    //票数
    private int spoll;
    //选项集合
    private List<Options> optionsList;
    public Subject(){}

    public Subject(int sid,String type){
        this.sid=sid;
        this.type=type;
    }
    public Subject(String title,String type){
        this.title=title;
        this.type=type;
    }
}

package com.cat.pojo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    //用户编号
    private int uid;
    //用户名
    private String username;
    //用户密码
    private String password;
    //是否管理员
    private String isAdmin;
}

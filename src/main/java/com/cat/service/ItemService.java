package com.cat.service;

import com.cat.pojo.Item;
import org.apache.ibatis.annotations.Param;

import java.sql.SQLException;
import java.util.List;

public interface ItemService {
    /**
     * 投票
     * @param items 投票集合
     * @return 执行结果
     */
    boolean addItem(List<Item> items)throws SQLException;

    /**
     * 查询用户是否投票
     * @param uid 用户编号
     * @return 查询结果
     */
    boolean selectItem(int sid,int uid);
}

package com.cat.dao;

import com.cat.pojo.Item;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemMapper {
    /**
     * 投票
     * @param items 投票集合
     * @return
     */
    int addItem(@Param("items") List<Item> items);
    /**
     * 查询用户是否投票
     * @param uid 用户编号
     * @return 查询结果
     */
    int selectItem(@Param("sid") int sid,@Param("uid") int uid);

    /**
     * 删除投票
     * @param sid 主题编号
     * @return 执行结果
     */
    int deleItem(@Param("sid") int sid);

    /**
     * 修改主题时删除投票
     * @param items 投票对象
     * @return 执行结果
     */
    int deleItemId(@Param("items")List<Item> items);
}

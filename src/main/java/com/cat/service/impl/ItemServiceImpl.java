package com.cat.service.impl;

import com.cat.dao.ItemMapper;
import com.cat.pojo.Item;
import com.cat.service.ItemService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
@Service("itemService")
@Transactional
public class ItemServiceImpl implements ItemService {
    @Resource(name = "itemMapper")
    private ItemMapper itemMap;
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,rollbackFor = SQLException.class)
    public boolean addItem(List<Item> items) throws SQLException {
        int result = itemMap.addItem(items);
        if(result > 0){
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public boolean selectItem(int sid,int uid) {
        int result = itemMap.selectItem(sid,uid);
        if(result > 0){
            return true;
        }
        return false;
    }

}

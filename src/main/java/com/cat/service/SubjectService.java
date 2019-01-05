package com.cat.service;

import com.cat.pojo.Options;
import com.cat.pojo.Subject;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface SubjectService {
    /**
     * 投票列表--分页
     * @param map 条件和页数
     * @return 集合
     */
    Page<Subject> selectVotelist(Map<String,String> map);
    /**
     * 查看投票
     * @param sid 主题id
     * @return 主题及选项对象
     */
    Subject seleVoteView(int sid);
    /**
     * 参与投票
     * @param sid 主题id
     * @return 主题及选项对象
     */
    Subject selectVote(int sid);

    /**
     * 投票主题验证
     * @param title 主题名
     * @return 查询结果
     */
    boolean verifyTitle(String title);

    /**
     * 添加主题
     * @param subject 主题对象
     * @param options 选项集合
     * @return 执行结果
     * @throws SQLException
     */
    boolean addSubject(Subject subject, List<Options> options) throws SQLException;

    /**
     * 修改主题
     * @param subject 主题对象
     * @param newOptions 选项集合
     * @return 执行结果
     * @throws SQLException
     */
    boolean updaSubject(Subject subject,List<Options> newOptions,String label)throws SQLException;

    /**
     * 删除主题
     * @param sid 主题编号
     * @return  执行结果
     */
    boolean deleSubject(@Param("sid") int sid)throws SQLException;
}

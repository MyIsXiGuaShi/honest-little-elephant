package com.cat.dao;


import com.cat.pojo.Subject;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;


public interface SubjectMapper {
    /**
     * 投票列表--分页
     * @param title 条件
     * @return 集合
     */
    Page<Subject> selectVotelist(@Param("title") String title);

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
    Integer verifyTitle(String title);

    /**
     * 添加主题
     * @param subject 主题对象
     * @return 执行结果
     */
    int addSubject(Subject subject);

    /**
     * 修改主题
     * @param subject 主题对象
     * @return 执行结果
     */
    int updaSubject(Subject subject);

    /**
     * 删除主题
     * @param sid 主题编号
     * @return  执行结果
     */
    int deleSubject(@Param("sid") int sid);
}

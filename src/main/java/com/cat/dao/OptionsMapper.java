package com.cat.dao;


import com.cat.pojo.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OptionsMapper {
    /**
     * 添加选项
     * @param options 选项集合
     * @return 执行结果
     */
    int addOptions(@Param("options") List<Options> options);

    /**
     * 更新选项
     * @param options 选项集合
     * @return 执行结果
     */
    int updaOptions(@Param("options")List<Options> options);

    /**
     * 修改主题时删除选项
     * @param options 选项集合
     * @return 执行结果
     */
    int deleOptions(@Param("options")List<Options> options);

    /**
     * 删除选项
     * @param osid  主题编号
     * @return 执行结果
     */
    int deleOptionsOsid(@Param("osid") int osid);
}

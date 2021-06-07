package com.cloud.olifebase.mapper;

import com.cloud.olifebase.entity.OExpert;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Abraham
 * @since 2021-05-18
 */
public interface OExpertMapper extends BaseMapper<OExpert> {

    @Select("SELECT e.* FROM`o_hospital` AS h " +
            "LEFT JOIN `o_expert` AS e ON h.`id` = e.`h_id` " +
            "WHERE h.`id`= #{id}")
    List<OExpert> getExpertByHospital(@Param("id") Long id);
}

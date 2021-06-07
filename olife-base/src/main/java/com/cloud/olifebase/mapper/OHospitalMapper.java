package com.cloud.olifebase.mapper;

import com.cloud.olifebase.entity.OHospital;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Abraham
 * @since 2021-05-18
 */
public interface OHospitalMapper extends BaseMapper<OHospital> {

    @Select("SELECT h.* FROM `o_expert` AS e " +
            "LEFT JOIN `o_hospital` AS h ON e.`h_id` = h.`id` " +
            "WHERE e.`h_id`= #{hId}")
    OHospital getHospitalByExpert(@Param("hId") Long hId);
}

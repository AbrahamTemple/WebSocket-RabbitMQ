package com.cloud.olifebase.mapper;

import com.cloud.olifebase.entity.OFamily;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cloud.olifebase.entity.OUser;
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
public interface OFamilyMapper extends BaseMapper<OFamily> {

    @Select("SELECT f.* FROM o_user AS u " +
            "LEFT JOIN o_family AS f ON u.id = f.id " +
            "WHERE u.id = #{id}")
    OFamily getFamilyByUser(@Param("id") Long id);

}

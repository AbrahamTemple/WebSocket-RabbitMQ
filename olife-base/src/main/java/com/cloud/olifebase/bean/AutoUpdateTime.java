package com.cloud.olifebase.bean;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class AutoUpdateTime implements MetaObjectHandler {

    static final Long ChinaZone = new Date().getTime()+28800000;//默认是美国时区

    @Override
    public void insertFill(MetaObject metaObject) {

        this.setFieldValByName("createTime",new Date(ChinaZone),metaObject);
        this.setFieldValByName("updateTime",new Date(ChinaZone),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("updateTime",new Date(ChinaZone),metaObject);
    }
}

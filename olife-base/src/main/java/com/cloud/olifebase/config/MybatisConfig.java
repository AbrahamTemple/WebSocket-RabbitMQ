package com.cloud.olifebase.config;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig {

    @Bean//Mybatis Plus分页配置
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setOverflow(true);//超过最大页数返回首页
        paginationInterceptor.setLimit(999);//设置最大页数
        return paginationInterceptor;//注册分页拦截器
    }

}

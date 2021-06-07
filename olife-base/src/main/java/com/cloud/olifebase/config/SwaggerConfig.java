package com.cloud.olifebase.config;

import com.cloud.olifebase.rest.BaseController;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

import static springfox.documentation.service.ApiInfo.DEFAULT_CONTACT;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.5.23
 * @GitHub https://github.com/AbrahamTemple/
 * @description: 访问：http://localhost:8081/swagger-ui.html
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket docket(Environment environment){
        Profiles profiles = Profiles.of("dev"); //找到spring.profiles.active为dev的值
        boolean isDevEnv = environment.acceptsProfiles(profiles); //是否为生产环境

        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())
                .groupName("OLife") //分组
                .enable(isDevEnv) //如果是生产环境就不启动swagger
                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.cloud.olifebase.rest")) //扫描指定的包
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.ant("/**")) //只能扫描/下的所有请求
                .build();
    }

    @Bean
    public ApiInfo apiInfo(){
        //作者信息
        Contact MY_CONTACT = new Contact("Abraham", "https://github.com/AbrahamTemple", "1135530168@qq.com");
        return new ApiInfo("亚伯拉罕的Swagger文档库", "欢迎访问该界面自定义引导：https://github.com/AbrahamTemple/Swagger2",
                "6.1.8","https://github.com/AbrahamTemple", MY_CONTACT,
                "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0", new ArrayList());
    }
}

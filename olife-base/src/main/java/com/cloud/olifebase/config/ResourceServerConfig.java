package com.cloud.olifebase.config;

import com.cloud.olifebase.security.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.*;

/**
 * @version 6.1.8
 * @author: Abraham Vong
 * @date: 2021.4.10
 * @GitHub https://github.com/AbrahamTemple/
 * @description: 资源服务器基本配置
 */
@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true,jsr250Enabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Value("${spring.cloud.nacos.discovery.ip}")
    private String ip;

    private static final String DEMO_RESOURCE_ID = "resid";

    @Bean
    UserDetailsService userDetailsService () {
        return new MyUserDetailsService();
    }

    /**
     * 实用程序界面，用于在Map之间进行用户身份验证
     * @return 从传入映射中提取{@link Authentication}时要使用的可选{@link UserDetailsService}
     */
    @Bean
    public UserAuthenticationConverter userAuthenticationConverter () {
        DefaultUserAuthenticationConverter authenticationConverter
                = new DefaultUserAuthenticationConverter();
        authenticationConverter.setUserDetailsService(userDetailsService());
        return authenticationConverter;
    }

    /**
     * 用于令牌服务实现的转换器接口，该接口将身份验证数据存储在令牌内。
     * @return 含用户身份策略的token转接器
     */
    @Bean
    public AccessTokenConverter accessTokenConverter() {
        DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
        tokenConverter.setUserTokenConverter(userAuthenticationConverter());
        return tokenConverter;
    }

    /**
     * 远程连接OAuth认证服务器进行关联token
     * @return 查询/ check_token端点以获取访问令牌的内容
     */
    @Bean
    RemoteTokenServices getRemoteTokenServices () {
        RemoteTokenServices rts = new RemoteTokenServices();
//        rts.setCheckTokenEndpointUrl("http://124.71.185.137:8077/oauth/check_token");
        rts.setCheckTokenEndpointUrl("http://"+ip+":8077/oauth/check_token");
        rts.setAccessTokenConverter(accessTokenConverter());
        rts.setClientId("clients");
        rts.setClientSecret("secrets");
        return rts;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable().
                authorizeRequests()
                .antMatchers("/v2/**","/webjars/**","/swagger/**","/swagger-ui.html","/swagger-resources/**")
                .permitAll()
                .anyRequest().authenticated();
        http.cors(); //开启跨域资源共享
    }

    /**
     * 资源id访问权限配置
     * @param resources 资源
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        //不配资源id就会报Invalid token does not contain resource id (oauth2-resource)
        resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
    }
}

package com.cloud.olifebase.security;

import com.cloud.olifebase.entity.OUser;
import com.cloud.olifebase.service.IOUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    private IOUserService vUserService;

    /**
     * 从数据库中查找并赋予SpringSecurity对应登录用户的信息
     * @param name
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        OUser loginUser = vUserService.getByName(name);
        if(loginUser==null) {
            throw new UsernameNotFoundException(name + "用户名不存在");
        }
        List<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
        roles.add(new SimpleGrantedAuthority(loginUser.getClientId()));
        return new User(loginUser.getUsername(),loginUser.getPassword(),roles);
    }
}

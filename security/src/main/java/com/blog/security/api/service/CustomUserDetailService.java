package com.blog.security.api.service;


import com.blog.common.model.RoleEnity;
import com.blog.common.model.UserLogin;
import com.blog.security.api.pojo.DfUser;
import com.blog.security.api.service.iml.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service(value = "customUserDetailService")
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserServiceInterface userService;

    @Override
    public DfUser loadUserByUsername(String account) throws UsernameNotFoundException {
        String loginType = "";
        if (account.contains(":")) {
            loginType = "Phone";
            account = account.substring(0, account.length() - 1);
        }
        UserLogin user = userService.loadUserByAccount(account);
        if (loginType == "Phone") {
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        }
        Set<GrantedAuthority> authorities = new HashSet<>();
        List<RoleEnity> roles = user.getRoles();
        for (RoleEnity r : roles) {
            authorities.add(new SimpleGrantedAuthority(r.getRname()));
        }
        return new DfUser(user.getUserId(), user.getShowName(), user.getAccount(), user.getIcon(), user.getPassword(), authorities);
    }
}

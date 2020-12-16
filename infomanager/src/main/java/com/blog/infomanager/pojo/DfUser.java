package com.blog.infomanager.pojo;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author jeffr
 */
@Data
public class DfUser extends User {
    private String userId;
    private String userName;
    private String userAccount;
    private String userIcon;

    public DfUser(String userId, String userName, String userAccount, String userIcon, String password, Collection<? extends GrantedAuthority> authorities) {
        super(userName, password, authorities);
        this.userName = userName;
        this.userAccount = userAccount;
        this.userId = userId;
        this.userIcon = userIcon;

    }
}

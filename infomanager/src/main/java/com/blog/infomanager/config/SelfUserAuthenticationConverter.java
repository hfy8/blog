package com.blog.infomanager.config;

import com.blog.common.model.LoginInfo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author jeffr
 */
@Component("DefaultUserAuthenticationConverter")
public class SelfUserAuthenticationConverter extends DefaultUserAuthenticationConverter {
    private static final String USER_ID = "userId";
    private static final String USER_NAME = "userName";
    private static final String N_A = "N/A";
    private Collection<? extends GrantedAuthority> defaultAuthorities;
    private UserDetailsService userDetailsService;

    public SelfUserAuthenticationConverter() {
        super();
    }

    @Override
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void setDefaultAuthorities(String[] defaultAuthorities) {
        this.defaultAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.arrayToCommaDelimitedString(defaultAuthorities));
    }

    @Override
    public Map<String, ?> convertUserAuthentication(Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap();
        response.put(USER_NAME, authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put("authorities", AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }

        return response;
    }

    @Override
    public Authentication extractAuthentication(Map<String, ?> map) {
        if (map.containsKey(USER_NAME)) {
            LoginInfo object=new LoginInfo();
            object.setUserName(map.get("userName").toString());
            object.setUserAccount(map.get("userAccount").toString());
            object.setUserIcon(map.get("userIcon").toString());
            object.setUserId(map.get("userId").toString());
            Object principal = object;
            Collection<? extends GrantedAuthority> authorities = this.getAuthorities(map);
            if (this.userDetailsService != null) {
                UserDetails user = this.userDetailsService.loadUserByUsername((String)map.get("userAccount"));
                authorities = user.getAuthorities();
                principal = user;
            }
            return new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        } else {
            return null;
        }
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey("authorities")) {
            return this.defaultAuthorities;
        } else {
            Object authorities = map.get("authorities");
            if (authorities instanceof String) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
            } else if (authorities instanceof Collection) {
                return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils.collectionToCommaDelimitedString((Collection) authorities));
            } else {
                throw new IllegalArgumentException("Authorities must be either a String or a Collection");
            }
        }
    }
}

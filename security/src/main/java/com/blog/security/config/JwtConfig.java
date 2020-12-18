package com.blog.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class JwtConfig {
    @Autowired
    UserDetailsService customUserDetailService;

    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        DefaultAccessTokenConverter defaultAccessTokenConverter = new DefaultAccessTokenConverter();
        SelfUserAuthenticationConverter selfUserAuthenticationConverter= new SelfUserAuthenticationConverter();
        selfUserAuthenticationConverter.setUserDetailsService(customUserDetailService);
        defaultAccessTokenConverter.setUserTokenConverter(selfUserAuthenticationConverter);
        converter.setAccessTokenConverter(defaultAccessTokenConverter);
        converter.setSigningKey("testKey");
        return converter;
    }

    @Bean
    public TokenEnhancer tokenEnhancer(){
        return new SelfTokenEnhancer();
    }
}

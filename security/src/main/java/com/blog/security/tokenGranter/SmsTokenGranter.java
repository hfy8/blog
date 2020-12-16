package com.blog.security.tokenGranter;/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.blog.common.model.UserLogin;
import com.blog.security.api.service.iml.UserInfoServiceInterface;
import com.blog.security.util.ApplicationContextUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;


public class SmsTokenGranter extends AbstractTokenGranter {

    private static final String GRANT_TYPE = "sms";

    public UserInfoServiceInterface userInfoService = ApplicationContextUtil.getBean("UserInfoServiceImp");

    private final AuthenticationManager authenticationManager;

    public SmsTokenGranter(AuthenticationManager authenticationManager,
                           AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory) {
        this(authenticationManager, tokenServices, clientDetailsService, requestFactory, GRANT_TYPE);
    }

    protected SmsTokenGranter(AuthenticationManager authenticationManager, AuthorizationServerTokenServices tokenServices,
                              ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, String grantType) {
        super(tokenServices, clientDetailsService, requestFactory, grantType);
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {

        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());
        String username = parameters.get("username");
        String password = parameters.get("password");
        // Protect from downstream leaks of password
        parameters.remove("password");

        Authentication userAuth = new UsernamePasswordAuthenticationToken(username, password);
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);
        try {
            userAuth = authenticationManager.authenticate(userAuth);
        } catch (AccountStatusException ase) {
            //covers expired, locked, disabled cases (mentioned in section 5.2, draft 31)
            throw new InvalidGrantException(ase.getMessage());
        } catch (BadCredentialsException e) {
            // If the username/password are wrong the spec says we should send 400/invalid grant
            throw new InvalidGrantException(e.getMessage());
        }
        if (userAuth == null || !userAuth.isAuthenticated()) {
            throw new InvalidGrantException("Could not authenticate user: " + username);
        }

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }

    /**
     * 自定义手机验证码校验
     */
    public void checkPhoneSms(Map<String, String> parameters) {
        String phone = (String) parameters.get("phone");
        String code = (String) parameters.get("code");//验证码

        if (StringUtils.isBlank(phone)) {
            throw new InvalidGrantException("手机号不能为空");
        }
        if (StringUtils.isBlank(code)) {
            throw new InvalidGrantException("验证码不能为空");
        }
        // 缓存中查询密码信息，如果没有就表示验证码失效
//        String codeCache = stringRedisTemplate.opsForValue().get("sms:" + phone);
//        if (StringUtils.isBlank(codeCache)) {
//            throw new InvalidGrantException("验证码已失效，请重新获取");
//        }
//        if (!code.equals(codeCache)) {
//            throw new InvalidGrantException("验证码错误");
//        }
        UserLogin user = userInfoService.getUserInfoByPhone(phone);
        parameters.put("username", user.getAccount() + ":");
        parameters.put("password", user.getPassword());
    }
}

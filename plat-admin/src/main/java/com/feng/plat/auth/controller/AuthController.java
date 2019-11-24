package com.feng.plat.auth.controller;

import com.feng.home.common.common.StringUtil;
import com.feng.home.common.resource.annotation.ResourceMeta;
import com.feng.home.common.validate.AssertUtil;
import com.feng.plat.auth.base.Token;
import com.feng.plat.auth.base.TokenStore;
import com.feng.home.plat.user.bean.SysUser;
import com.feng.plat.user.service.SysUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Resource
    private TokenStore<SysUser> tokenStore;

    @Resource
    private SysUserService sysUserService;

    @RequestMapping(value = "/accessToken", method = RequestMethod.GET)
    public Token accessToken(String username, String password){
        AssertUtil.assertTrue(StringUtil.isNotEmpty(username) && StringUtil.isNotEmpty(password), "用户名和密码不能为空");
        SysUser sysUser = sysUserService.login(username, password);
        Token token = tokenStore.messageToToken(sysUser);
        return token;
    }

    @RequestMapping(value = "/checkToken")
    @ResourceMeta(code = "CURRENT_USER", resourceName = "当前用户信息", url = "/auth/checkToken", group = "plat", enableAuthCheck = false)
    public SysUser checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        return tokenStore.tokenToMessage(token).orElse(null);
    }
}

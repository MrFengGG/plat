package com.feng.plat.auth.controller;

import com.feng.home.common.common.StringUtil;
import com.feng.home.common.validate.AssertUtil;
import com.feng.plat.auth.base.Token;
import com.feng.plat.auth.base.TokenStore;
import com.feng.plat.user.bean.SysUser;
import com.feng.plat.user.service.SysUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Resource
    private TokenStore<SysUser> tokenStore;

    @Resource
    private SysUserService sysUserService;

    @RequestMapping(value = "/accessToken")
    public Token accessToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        AssertUtil.assertTrue(StringUtil.isNotEmpty(username) && StringUtil.isNotEmpty(password), "用户名和密码不能为空");
        String originUrl = request.getParameter("originUrl");
        SysUser sysUser = sysUserService.login(username, password);
        Token token = tokenStore.messageToToken(sysUser);
        if(StringUtil.isNotEmpty(originUrl)) {
            response.sendRedirect(originUrl + "?" + token.getToken());
        }
        return token;
    }

    @RequestMapping(value = "/checkToken")
    public SysUser checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        return tokenStore.tokenToMessage(token).orElse(null);
    }
}

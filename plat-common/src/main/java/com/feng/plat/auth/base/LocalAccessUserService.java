package com.feng.plat.auth.base;

import com.feng.home.common.auth.bean.ContextUser;
import com.feng.home.common.auth.service.AccessUserService;
import com.feng.home.common.exception.AuthException;
import com.feng.home.common.exception.ForbiddenException;
import com.feng.home.common.exception.InvalidTokenException;
import com.feng.home.plat.user.bean.SysUser;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * create by FengZiyu
 * 2019/11/22
 */
public class LocalAccessUserService implements AccessUserService {

    @Resource
    private MemoryUserTokenStore memoryUserTokenStore;

    @Override
    public Optional<ContextUser> accessUser(HttpServletRequest httpServletRequest) throws ForbiddenException, AuthException, InvalidTokenException {
        String token = httpServletRequest.getHeader("token");
        Optional<SysUser> sysUserOptional =  memoryUserTokenStore.tokenToMessage(token);
        return sysUserOptional.map( sysUser -> ContextUser.builder().username(sysUser.getUsername()).roleList(sysUser.getRoles()).build());
    }
}

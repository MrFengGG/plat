package com.feng.plat.auth.base;


import com.feng.home.common.common.UIDUtils;
import com.feng.home.plat.user.bean.SysUser;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Component
public class MemoryUserTokenStore implements TokenStore<SysUser> {
    private Map<String, SysUser> tokenStore = new ConcurrentHashMap<>();

    private Map<String, LocalTime> tokenTimeMap = new ConcurrentHashMap<>();

    public MemoryUserTokenStore(){
        //定时清除过期token
        startClean(60 * 30);
    }

    public MemoryUserTokenStore(long expireAfterSeconds){
        //定时清除过期token
        startClean(expireAfterSeconds);
    }

    @Override
    public Token messageToToken(SysUser userInfo) {
        String token = UIDUtils.generateUuid();
        tokenStore.put(token, userInfo);
        return genToken(token);
    }

    @Override
    public Optional<SysUser> tokenToMessage(String token) {
        Optional<SysUser> userInfo = Optional.ofNullable(tokenStore.get(token));
        if(userInfo.isPresent()){
            //刷新token失效时间
            tokenTimeMap.put(token, LocalTime.now());
        }
        return userInfo;
    }

    @Override
    public Token refreshToken(String token) {
        if(tokenToMessage(token).isPresent()){
            tokenTimeMap.put(token, LocalTime.now());
        }
        return genToken(token);
    }

    private Token genToken(String token){
        return Token.builder().expireTime(LocalTime.now().atOffset(ZoneOffset.ofHoursMinutes(0, 30))
                .toLocalTime()).token(token).build();
    }

    private void startClean(long expireAfter){
        //定时清除过期token
        new ScheduledThreadPoolExecutor(1).scheduleAtFixedRate(() ->{
            tokenTimeMap.entrySet().stream()
                    .filter(entry -> Duration.between(entry.getValue(), LocalTime.now()).getSeconds() > expireAfter)
                    .map(Map.Entry::getKey)
                    .forEach(tokenStore::remove);
        }, expireAfter, expireAfter, TimeUnit.SECONDS);
    }

}

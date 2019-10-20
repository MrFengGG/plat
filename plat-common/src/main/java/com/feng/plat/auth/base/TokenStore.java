package com.feng.plat.auth.base;

import java.util.Optional;

public interface TokenStore<T> {
    public Token messageToToken(T t);

    public Optional<T> tokenToMessage(String token);

    public Token refreshToken(String token);
}

package com.feng.plat.auth.base;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

@Builder
@Data
public class Token implements Serializable {
    private String token;

    private LocalTime expireTime;
}

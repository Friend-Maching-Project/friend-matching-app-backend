package com.example.potato.sic9.common;

public enum ExpireTime {
    REFRESH_COOKIE_EXPIRE_TIME(60 * 60 * 10),
    ACCESS_TOKEN_EXPIRE_TIME(1000 * 60 * 60),
    REFRESH_TOKEN_EXPIRE_TIME(1000 * 60 * 60 * 10);

    private final long time;

    ExpireTime(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}

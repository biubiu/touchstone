package com.shawn.touchstone.di.beans;

import com.google.common.base.MoreObjects;

public class RedisCounter {
    private String ipAddress;
    private Integer port;

    public RedisCounter(String ipAddress, Integer port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("ipAddress", ipAddress)
                .add("port", port)
                .toString();
    }
}

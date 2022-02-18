package com.microsoft.gbb.redisdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

@Service
@Profile("prod")
public class RedisCluster implements RedisInterface {

    @Autowired
    JedisCluster jedisCluster;

    @Override
    public String getKey(String key) {
        return jedisCluster.get(key);
    }
}

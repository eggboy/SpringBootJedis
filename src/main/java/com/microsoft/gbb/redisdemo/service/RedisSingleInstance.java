package com.microsoft.gbb.redisdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
@Profile("dev")
public class RedisSingleInstance implements RedisInterface{

    @Autowired
    JedisPool jedisPool;

    @Override
    public String getKey(String key) {
        String value = "";
        try(Jedis jedis = jedisPool.getResource()){
            value = jedis.get(key);
            try {
                Thread.sleep(100000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return value;
    }
}

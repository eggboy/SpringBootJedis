package com.microsoft.gbb.redisdemo.controller;

import com.microsoft.gbb.redisdemo.service.RedisInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import java.util.Optional;

@RestController
public class JedisTestController {

    private RedisInterface redis;

    public JedisTestController(RedisInterface redis) {
        this.redis = redis;
    }

    @GetMapping("/data/{redisKey}")
    public String getData(@PathVariable String redisKey) {
        String redisResponse = Optional.ofNullable(redis.getKey(redisKey)).orElseGet(() -> "").trim();

        return redisResponse;
    }
}

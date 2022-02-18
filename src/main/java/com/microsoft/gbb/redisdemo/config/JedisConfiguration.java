package com.microsoft.gbb.redisdemo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.clients.jedis.*;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Configuration
public class JedisConfiguration {

	int redisPoolMinSize = 50;
	int redisPoolMaxSize = 50;

	@Value("${redisUrl}")
	private String redisUrl;

	@Value("${redisPort}")
	private int redisPort;

	@Value("${redisSSLPort}")
	private int redisSSLPort;

	@Value("${redisPassword}")
	private String REDIS_PASSWORD;

	private boolean REDIS_SSL = true;

	private int DEFAULT_TIMEOUT = 60;

	private int DEFAULT_MAX_REDIRECTION = 60;

	@Bean
	@Profile("prod")
	public JedisCluster getJedisClusterProd() {
		Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
		jedisClusterNodes.add(new HostAndPort(redisUrl, redisSSLPort));

		JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT,
				DEFAULT_MAX_REDIRECTION, REDIS_PASSWORD, "localjedis", initPoolcontiguration(), REDIS_SSL);

		return jedisCluster;
	}

	@Bean
	@Profile("dev")
	public JedisPool getJedis() {
		JedisPool jedisPool = new JedisPool(initPoolcontiguration(), redisUrl, redisPort, DEFAULT_TIMEOUT,
				REDIS_PASSWORD);

		return jedisPool;
	}

	private JedisPoolConfig initPoolcontiguration() {

		final JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxIdle(redisPoolMaxSize);
		config.setMinIdle(redisPoolMinSize);
		config.setMaxTotal(redisPoolMaxSize);
		config.setTestOnBorrow(false);
		config.setTestOnReturn(false);
		config.setTestWhileIdle(true);
		config.setMinEvictableIdleTime(Duration.ofSeconds(60));
		config.setNumTestsPerEvictionRun(3);
		config.setBlockWhenExhausted(true);
		config.setMaxWait(Duration.ofSeconds(60));

		return config;
	}

}

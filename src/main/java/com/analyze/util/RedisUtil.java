package com.analyze.util;

import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Redis连接池
 * 
 * @author wei
 * 
 */
public final class RedisUtil {
	private static String ADDR = "";
	private static int PORT = 0;
	private static String AUTH = "";
	private static int MAX_ACTIVE = 0;
	private static int MAX_IDLE = 0;
	private static long MAX_WAIT = 0L;
	private static int TIMEOUT = 0;
	private static boolean TEST_ON_BORROW = true;
	private static JedisPool jedisPool = null;

	/**
	 * 初始化Redis连接池
	 */
	static {
		try {
			Properties p = PropertiesUtils.getProperties("redis.properties");
			ADDR = p.getProperty("redis.host");
			PORT = Integer.parseInt(p.getProperty("redis.port"));
			AUTH = p.getProperty("AUTH");
			MAX_ACTIVE = Integer.parseInt(p.getProperty("redis.max_active"));
			MAX_IDLE = Integer.parseInt(p.getProperty("redis.max_idle"));
			MAX_WAIT = Long.parseLong(p.getProperty("redis.max_wait"));
			TIMEOUT = Integer.parseInt(p.getProperty("redis.timeout"));
			TEST_ON_BORROW = Boolean.parseBoolean(p.getProperty("redis.test_on_borrow"));

			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT);// 没有设置密码，有密码的话直接添加参数

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取Jedis实例
	 * 
	 * @return
	 */
	public synchronized static Jedis getJedis() {
		try {
			if (jedisPool != null) {
				Jedis resource = jedisPool.getResource();
				return resource;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.err.println("redis服务器异常："+e.getMessage());
			return null;
		}
	}

	/**
	 * 释放jedis资源
	 * 
	 * @param jedis
	 */
	@SuppressWarnings("deprecation")
	public static void returnResource(final Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Jedis jedis = getJedis();
		jedis.set("name", "wei");
		jedis.set("aaa", "bbb");
		System.out.println(jedis.get("aaa"));
	}
}
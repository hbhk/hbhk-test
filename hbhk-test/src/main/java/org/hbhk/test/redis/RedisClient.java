package org.hbhk.test.redis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisClient {

	private Jedis jedis;// 非切片额客户端连接
	private JedisPool jedisPool;// 非切片连接池
	private ShardedJedis shardedJedis;// 切片额客户端连接
	private ShardedJedisPool shardedJedisPool;// 切片连接池
	private String ip;
	private int port;

	private JedisSentinelPool jedisSentinelPool;
	
	public RedisClient(String master,Set<String> sentinels) {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(20);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(10 * 1000);
		config.setTestOnBorrow(false);
		jedisSentinelPool = new JedisSentinelPool(master, sentinels,config);
		jedis = jedisSentinelPool.getResource();
	}

	/**
	 * 初始化非切片池
	 */
	private void initialPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(20);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(10 * 1000);
		config.setTestOnBorrow(false);
		jedisPool = new JedisPool(config, this.ip, this.port);
	}

	/**
	 * 初始化切片池
	 */
	private void initialShardedPool() {
		// 池基本配置
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(20);
		config.setMaxIdle(5);
		config.setMaxWaitMillis(10 * 1000);
		config.setTestOnBorrow(false);
		// slave链接
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo(this.ip, this.port, "master"));

		// 构造池
		shardedJedisPool = new ShardedJedisPool(config, shards);
	}

	public void show() {
		// KeyOperate();
		// StringOperate();
		// ListOperate();
		// SetOperate();
		// SortedSetOperate();
		// HashOperate();
		jedisPool.returnResource(jedis);
		shardedJedisPool.returnResource(shardedJedis);
	}

	public boolean KeyOperate(String key) {
		return jedis.exists(key);
	}

	public String StringOperate(String key) {
		return jedis.get(key);
	}
	public String stringHgetOperate(String key ,String field ) {
		return jedis.hget(key, field);
	}

	public void ListOperate() {
	}

	public void SetOperate() {
	}

	public void SortedSetOperate() {
	}

	public void HashOperate() {
	}
}
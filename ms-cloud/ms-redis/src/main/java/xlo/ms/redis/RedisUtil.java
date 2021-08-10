package xlo.ms.redis;

import xlo.ms.redis.util.*;

/**
 * @author XiaoLOrange
 * @time 2021.08.04
 * @title
 */

public interface RedisUtil extends RedisStringUtil, RedisListUtil, RedisHashUtil, RedisSetUtil, RedisZSetUtil
, RedisClusterUtil, RedisGeoUtil, RedisHyperLogLogUtil, RedisStreamUtil{

	/**
	 * 事务，在rt中输入需要执行事物的语句
	 * @param rt
	 * @param <T>
	 * @return
	 * @throws RuntimeException
	 */
	<T> T Transaction(RedisTransaction<T> rt) throws RuntimeException;

	/**
	 * 设置过期时间，单位：秒
	 * @see org.springframework.data.redis.core.RedisOperations#expire(java.lang.Object, long, java.util.concurrent.TimeUnit)
	 * @param key
	 * @param time <= 0 直接过期
	 * @return
	 */
	boolean expire(String key, long time);

	/**
	 * 获取过期时间，单位s
	 * @see org.springframework.data.redis.core.RedisOperations#getExpire(java.lang.Object, java.util.concurrent.TimeUnit)
	 * @param key
	 * @return -1 = 永久有效
	 */
	long getExpire(String key);

	/**
	 * 是否存在key
	 * @param key
	 * @return
	 */
	boolean hasKey(String key);

	/**
	 * 删除缓存
	 * @param key
	 * @return
	 */
	boolean delKey(String key);

	/**
	 * 返回删除的key数量
	 * @param keys
	 * @return The number of keys that were removed. {@literal null} when used in pipeline / transaction.
	 */
	Long delKey(String... keys);

}

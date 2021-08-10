package xlo.ms.redis.util;

/**
 * @author XiaoLOrange
 * @time 2021.08.04
 * @title redis 字符串类型操作
 */

public interface RedisStringUtil {

	/**
	 * 通过键获取值
	 * @param key
	 * @return
	 */
	Object get(String key);

	/**
	 * 存入缓存
	 * @param key
	 * @param value
	 * @return
	 */
	boolean set(String key, Object value);

	/**
	 * 存入缓存
	 * @param key
	 * @param value
	 * @param time
	 * @return
	 */
	boolean set(String key, Object value, long time);

	/**
	 * 计数器
	 * @param key 存在则值+1，不存在则初始化后+1
	 * @return 当前的值
	 */
	Long increment(String key);

	/**
	 * 计数器
	 * @param key 存在则值+1，不存在则初始化后+1
	 * @param step
	 * @return 当前的值
	 */
	Long increment(String key, long step);

}

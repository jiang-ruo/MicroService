package xlo.ms.redis.util;

import java.util.Map;

/**
 * @author XiaoLOrange
 * @time 2021.08.10
 * @title
 */

public interface RedisHashUtil {

	/**
	 *
	 * @param key
	 * @param item
	 * @return
	 */
	Object hget(Object key, Object item);

	/**
	 *
	 * @param key
	 * @return
	 */
	Map hget(Object key);

	/**
	 *
	 * @param key
	 * @param iMap
	 * @return
	 */
	boolean hset(Object key, Map iMap);

	boolean hset(Object key, Map iMap, long time);

	boolean hset(Object key, Object item, Object value);

	boolean hset(Object key, Object item, Object value, long time);

	/**
	 * 删除
	 * @param key
	 * @param items
	 * @return
	 */
	boolean hdel(Object key, Object... items);

	boolean hHasItem(Object key, Object item);

	/**
	 * 递增，不存在则创建，存在则增长后返回
	 * @param key
	 * @param item
	 * @return
	 */
	double hIncrement(Object key, Object item);

	/**
	 * 递增，不存在则创建，存在则增长后返回
	 * @param key
	 * @param item
	 * @param step 步长
	 * @return
	 */
	double hIncrement(Object key, Object item, double step);

}

package xlo.ms.redis.util;

import java.util.Set;

/**
 * @author XiaoLOrange
 * @time 2021.08.10
 * @title
 */

public interface RedisSetUtil {

	Set sget(Object key);

	boolean sHasValue(Object key, Object value);

	boolean sset(Object key, Object... values);

	boolean sset(Object key, long time, Object... values);

	long ssize(Object key);

	/**
	 * 移除
	 * @param key
	 * @param values
	 * @return 移除的个数
	 */
	long sremove(Object key, Object... values);

}

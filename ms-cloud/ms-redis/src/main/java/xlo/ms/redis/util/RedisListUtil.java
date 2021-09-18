package xlo.ms.redis.util;

import java.util.List;

/**
 * @author XiaoLOrange
 * @time 2021.08.10
 * @title
 */

public interface RedisListUtil {

	/**
	 * 获取list缓存的内容
	 * @param key
	 * @return
	 */
	default List lget(Object key){
		return lget(key, 0, -1);
	}

	/**
	 * 获取list缓存的内容
	 * @param key
	 * @param start
	 * @param end
	 * start = 0, end = -1 代表所有值
	 * @return
	 */
	List lget(Object key, long start, long end);

	/**
	 * 向集合内添加元素
	 * @param key
	 * @param value
	 * @return 返回该集合的元素数量
	 */
	boolean lpush(Object key, Object value);

	boolean lpush(Object key, Object value, long time);

	boolean lpush(Object key, List value);

	boolean lpush(Object key, List value, long time);

	Object lpop(Object key);

	/**
	 * 获取list长度
	 * @param key
	 * @return
	 */
	long lsize(Object key);

	/**
	 * 获取指定list中的值
	 * @param key 0 - 第一个元素，-1 倒一个元素
	 * @param index
	 * @return
	 */
	Object lget(Object key, long index);

	/**
	 * 修改某条数据
	 * @param key
	 * @param index
	 * @param value
	 * @return
	 */
	boolean lupdate(Object key, long index, Object value);

	/**
	 * 移除N个值为value
	 * @param key
	 * @param count
	 * @param value
	 * @return 移除的个数
	 */
	long lremove(Object key, long count, Object value);
}

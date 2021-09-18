package xlo.ms.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.*;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author XiaoLOrange
 * @time 2021.08.04
 * @title 通过该类存入redis的数据定有过期时间，默认为1天
 */

@ConditionalOnMissingBean(name="redisUtil")
public class DefaultRedisUtil implements RedisUtil{

	/**
	 * 默认缓存时间，为1天
	 */
	private static final long DEFAULT_EXPIRE = 60 * 60 * 24;
	/**
	 * 默认自增长步长
	 */
	private static final int DEFAULT_STEP = 1;

	@Resource(name = "redisTemplate")
	private RedisTemplate rt;

	@Resource
	private ValueOperations vo;
	@Resource
	private ListOperations lo;
	@Resource
	private HashOperations ho;
	@Resource
	private SetOperations so;
//	@Resource
//	private ZSetOperations zso;
//	@Resource
//	private ClusterOperations co;
//	@Resource
//	private GeoOperations go;
//	@Resource
//	private HyperLogLogOperations hllo;
//	@Resource
//	private StreamOperations so;

	@Override
	public <T> T Transaction(RedisTransaction<T> rt) throws RuntimeException {
		return (T) this.rt.execute(new SessionCallback<T>() {
			@Override
			public T execute(RedisOperations ro) throws DataAccessException {
				ro.multi();
				T rs = rt.exec();
				ro.exec();
				return rs;
			}
		});
	}

	@Override
	public boolean expire(Object key, long time){
		try {
			rt.expire(key, time, TimeUnit.SECONDS);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public long getExpire(Object key) {
		return rt.getExpire(key, TimeUnit.SECONDS);
	}

	@Override
	public boolean hasKey(Object key) {
		return rt.hasKey(key);
	}

	@Override
	public boolean delKey(Object key) {
		return rt.delete(key);
	}

	@Override
	public Long delKey(Object... keys) {
		return rt.delete(CollectionUtils.arrayToList(keys));
	}

	@Override
	public Object get(Object key) {
		return key == null ? null : vo.get(key);
	}

	@Override
	public boolean set(Object key, Object value) {
		return this.set(key, value, DEFAULT_EXPIRE);
	}

	@Override
	public boolean set(Object key, Object value, long time) {
		try {
			vo.set(key, value);
			rt.expire(key, time, TimeUnit.SECONDS);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Long increment(Object key) {
		return vo.increment(key, DEFAULT_STEP);
	}

	@Override
	public Long increment(Object key, long step) {
		return vo.increment(key, step);
	}

	@Override
	public List lget(Object key, long start, long end) {
		return lo.range(key, start, end);
	}

	@Override
	public boolean lpush(Object key, Object value) {
		return lpush(key, value, DEFAULT_EXPIRE);
	}

	@Override
	public boolean lpush(Object key, Object value, long time) {
		try {
			lo.rightPush(key, value);
			expire(key, time);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean lpush(Object key, List value) {
		return lpush(key, value, DEFAULT_EXPIRE);
	}

	@Override
	public boolean lpush(Object key, List value, long time) {
		try {
			lo.rightPushAll(key, value);
			expire(key, time);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Object lpop(Object key) {
		return lo.rightPop(key);
	}

	@Override
	public long lsize(Object key) {
		return lo.size(key);
	}

	@Override
	public Object lget(Object key, long index) {
		return lo.index(key, index);
	}

	@Override
	public boolean lupdate(Object key, long index, Object value) {
		try {
			lo.set(key, index, value);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public long lremove(Object key, long count, Object value) {
		return lo.remove(key, count, value);
	}

	@Override
	public Object hget(Object key, Object item) {
		return ho.get(key, item);
	}

	@Override
	public Map hget(Object key) {
		return ho.entries(key);
	}

	@Override
	public boolean hset(Object key, Map iMap) {
		return hset(key, iMap, DEFAULT_EXPIRE);
	}

	@Override
	public boolean hset(Object key, Map iMap, long time) {
		try {
			ho.putAll(key, iMap);
			expire(key, time);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean hset(Object key, Object item, Object value) {
		return hset(key, item,value, DEFAULT_EXPIRE);
	}

	@Override
	public boolean hset(Object key, Object item, Object value, long time) {
		try {
			ho.put(key, item, value);
			expire(key, time);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean hdel(Object key, Object... items) {
		try {
			ho.delete(key, items);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean hHasItem(Object key, Object item) {
		return ho.hasKey(key, item);
	}

	@Override
	public double hIncrement(Object key, Object item) {
		return hIncrement(key, item, DEFAULT_STEP);
	}

	@Override
	public double hIncrement(Object key, Object item, double step) {
		return ho.increment(key, item, step);
	}

	@Override
	public Set sget(Object key) {
		return so.members(key);
	}

	@Override
	public boolean sHasValue(Object key, Object value) {
		return so.isMember(key, value);
	}

	@Override
	public boolean sset(Object key, Object... values) {
		return sset(key, DEFAULT_EXPIRE, values);
	}

	@Override
	public boolean sset(Object key, long time, Object... values) {
		try {
			so.add(key, values);
			expire(key, time);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public long ssize(Object key) {
		return so.size(key);
	}

	@Override
	public long sremove(Object key, Object... values) {
		return so.remove(key, values);
	}
}

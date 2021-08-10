package xlo.ms.redis;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
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
	public boolean expire(String key, long time){
		try {
			rt.expire(key, time, TimeUnit.SECONDS);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public long getExpire(String key) {
		return rt.getExpire(key, TimeUnit.SECONDS);
	}

	@Override
	public boolean hasKey(String key) {
		return rt.hasKey(key);
	}

	@Override
	public boolean delKey(String key) {
		return rt.delete(key);
	}

	@Override
	public Long delKey(String... keys) {
		return rt.delete(CollectionUtils.arrayToList(keys));
	}

	@Override
	public Object get(String key) {
		return key == null ? null : vo.get(key);
	}

	@Override
	public boolean set(String key, Object value) {
		return this.set(key, value, DEFAULT_EXPIRE);
	}

	@Override
	public boolean set(String key, Object value, long time) {
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
	public Long increment(String key) {
		return vo.increment(key, DEFAULT_STEP);
	}

	@Override
	public Long increment(String key, long step) {
		return vo.increment(key, step);
	}
}

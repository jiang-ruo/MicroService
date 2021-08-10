package xlo.ms.redis;

/**
 * @author XiaoLOrange
 * @time 2021.08.05
 * @title
 */

public interface RedisTransaction<T> {

	T exec();

}

package xlo.ms.redis;

/**
 * @author XiaoLOrange
 * @time 2021.08.05
 * @title 用于执行事务类
 */

public interface RedisTransaction<T> {

	T exec();

}

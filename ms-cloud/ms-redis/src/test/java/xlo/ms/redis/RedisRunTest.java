package xlo.ms.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author XiaoLOrange
 * @time 2021.08.04
 * @title
 */

public class RedisRunTest {

	@Test
	public void rrt(){
		//连接本地的 Redis 服务
		Jedis jedis = new Jedis("39.108.224.191", 6379);
		// 如果 Redis 服务设置了密码，需要下面这行，没有就不需要
		jedis.auth("redis123456");
		System.out.println("连接成功");
		//查看服务是否运行
		System.out.println("服务正在运行: "+jedis.ping());
	}

}

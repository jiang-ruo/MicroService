package xlo.ms.redis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

/**
 * @author XiaoLOrange
 * @time 2021.08.03
 * @title
 */

@SpringBootTest
public class MsRedisTest {

	@Resource
	private RedisTemplate rt;

	@Test
	public void mt(){
		System.out.println(rt);
	}

}

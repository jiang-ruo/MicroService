package xlo.ms.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xlo.ms.redis.RedisUtil;
import xlo.ms.test.bean.RedisTestBean;

/**
 * @author XiaoLOrange
 * @time 2021.08.03
 * @title
 */

@RestController
@RequestMapping("redis")
public class RedisTestController {

	@Autowired
	private RedisUtil ru;

	@RequestMapping("1")
	public boolean tc(){
//		ru.set("aaa", "sdfsdf");
		RedisTestBean rtb = new RedisTestBean();
		rtb.setRedisInt(20);
		// 事务
//		ru.Transaction(() -> {
//			ru.set(rtb, "xc");
//			return null;
//		});
		System.out.println(ru.get(rtb));
		return true;
	}

}

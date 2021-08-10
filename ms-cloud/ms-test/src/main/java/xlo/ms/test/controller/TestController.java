package xlo.ms.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xlo.ms.redis.RedisUtil;

/**
 * @author XiaoLOrange
 * @time 2021.08.03
 * @title
 */

@RestController
public class TestController {

	@Autowired
	private RedisUtil ru;

	@RequestMapping("/")
	public boolean tc(){
//		ru.set("aaa", "sdfsdf");
		ru.Transaction(() -> {
			ru.set("aaa", "xc");
			return null;
		});
		System.out.println(ru.get("aaa"));
		return true;
	}

}

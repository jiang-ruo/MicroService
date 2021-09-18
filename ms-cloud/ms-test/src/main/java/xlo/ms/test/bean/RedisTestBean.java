package xlo.ms.test.bean;

import lombok.Data;

/**
 * @author XiaoLOrange
 * @time 2021.09.17
 * @title
 */

@Data
public class RedisTestBean {

	private String redis;
	private int redisInt;

	public RedisTestBean(){
		this.redis = "redis";
		this.redisInt = 10;
	}

}

package xlo.ms.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @author XiaoLOrange
 * @time 2021.08.03
 * @title https://www.cnblogs.com/superfj/p/9232482.html
 */

@Setter
@ToString
@PropertySource("classpath:/config.properties")
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConfig {

//	/**
//	 * redis数据库索引
//	 */
//	@Value("${spring.redis.database}")
//	private int database;
//
//	@Value("${spring.redis.host}")
//	private String host;
//
//	@Value("${spring.redis.port}")
//	private int port;
//
//	@Value("${spring.redis.password}")
//	private int password;
//
//	/**
//	 * 连接池最大连接数，<0=无限制
//	 */
//	@Value("${spring.redis.pool.max-active}")
//	private int maxActive;
//
//	/**
//	 * 连接池最大阻塞等待时间，<0=无限制，毫秒
//	 */
//	@Value("${spring.redis.pool.max-wait}")
//	private int maxWait;
//
//	/**
//	 * 连接池中的最大空闲连接
//	 */
//	@Value("${spring.redis.pool.max-idle}")
//	private int maxIdle;
//
//	/**
//	 * 连接池中的最小空闲连接
//	 */
//	@Value("${spring.redis.pool.min-idle}")
//	private int minIdle;
//
//	/**
//	 * 连接池中的最小空闲连接
//	 */
//	@Value("${spring.redis.timeout}")
//	private int timeout;

	/**
	 * 因为{@link RedisAutoConfiguration}中的RedisTemplate使用不方便，因此再创建一个RedisTemplate
	 * @param factory
	 * @return
	 */
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory){
		RedisTemplate<Object, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(factory);
		// 因为jdk序列化的缺点：1、需要实现Serializable接口，
		//					 2、序列化后为二进制数据，无法转为多个不同的类
		//					 3、jdk序列化后大小为json的5倍
		// 因此使用其它的序列化方式
		// 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
		Jackson2JsonRedisSerializer jackson = new Jackson2JsonRedisSerializer(Object.class);

		// 指定序列化的域，非final类
		ObjectMapper om = new ObjectMapper();
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		jackson.setObjectMapper(om);

		// key和hash key采用StringRedisSerializer序列化
//		StringRedisSerializer srs = new StringRedisSerializer();
//		template.setKeySerializer(srs);
//		template.setHashKeySerializer(srs);
		// 不知道网上为什么都选择key为String类型
		template.setKeySerializer(jackson);
		template.setHashKeySerializer(jackson);
		// value和hash value采用json序列化
		template.setValueSerializer(jackson);
		template.setHashValueSerializer(jackson);

		template.afterPropertiesSet();
		return template;
	}

	/**
	 * 对字符串类型的操作
	 * @param rt
	 * @return
	 */
	@Bean
	public ValueOperations valueOperations(@Qualifier("redisTemplate") RedisTemplate rt){
		return rt.opsForValue();
	}

	/**
	 * 对链表型数据的操作
	 * @param rt
	 * @return
	 */
	@Bean
	public ListOperations listOperations(@Qualifier("redisTemplate") RedisTemplate rt){
		return rt.opsForList();
	}

	/**
	 * 对hash类型的数据操作
	 * @param rt
	 * @return
	 */
	@Bean
	public HashOperations hashOperations(@Qualifier("redisTemplate") RedisTemplate rt){
		return rt.opsForHash();
	}

	/**
	 * 对无序集合类型的数据操作
	 * @param rt
	 * @return
	 */
	@Bean
	public SetOperations setOperations(@Qualifier("redisTemplate") RedisTemplate rt){
		return rt.opsForSet();
	}

	/**
	 * 对有序集合类型的数据操作
	 * @param rt
	 * @return
	 */
	@Bean
	public ZSetOperations zSetOperations(@Qualifier("redisTemplate") RedisTemplate rt){
		return rt.opsForZSet();
	}

	@Bean
	public ClusterOperations clusterOperations(@Qualifier("redisTemplate") RedisTemplate rt){
		return rt.opsForCluster();
	}

	@Bean
	public GeoOperations geoOperations(@Qualifier("redisTemplate") RedisTemplate rt){
		return rt.opsForGeo();
	}

	@Bean
	public HyperLogLogOperations hyperLogLogOperations(@Qualifier("redisTemplate") RedisTemplate rt){
		return rt.opsForHyperLogLog();
	}

	@Bean
	public StreamOperations streamOperations(@Qualifier("redisTemplate") RedisTemplate rt){
		return rt.opsForStream();
	}

}

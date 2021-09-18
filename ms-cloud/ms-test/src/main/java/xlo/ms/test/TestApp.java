package xlo.ms.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author XiaoLOrange
 * @time 2021.08.02
 * @title
 */

@EnableCaching
@EnableEurekaClient
@SpringBootApplication
public class TestApp {

	public static void main(String[] args) {
		SpringApplication.run(TestApp.class, args);
	}

}

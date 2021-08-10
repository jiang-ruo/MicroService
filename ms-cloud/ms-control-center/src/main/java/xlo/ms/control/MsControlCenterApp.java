package xlo.ms.control;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author XiaoLOrange
 * @time 2021.08.02
 * @title
 */

@EnableConfigServer
@EnableEurekaServer
@SpringBootApplication
public class MsControlCenterApp {

	public static void main(String[] args) {
		SpringApplication.run(MsControlCenterApp.class, args);
	}

}

package xlo.ms.control.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XiaoLOrange
 * @time 2021.08.02
 * @title
 */

@RestController
@RequestMapping("/config")
public class ConfigController {

	@RequestMapping("/config/aaa.js")
	public String tc(){
		return "sdf";
	}

}

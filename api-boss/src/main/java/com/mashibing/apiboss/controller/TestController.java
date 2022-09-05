package com.mashibing.apiboss.controller;

import com.mashibing.apiboss.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2022/9/5 - 15:28
 */
@RestController
public class TestController {

	@Autowired
	private TestService testService;

	@GetMapping("/test")
	public String test() {
		return testService.test();
	}
}

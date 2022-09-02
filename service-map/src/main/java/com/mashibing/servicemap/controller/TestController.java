package com.mashibing.servicemap.controller;

import com.mashibing.servicemap.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2022/8/31 - 9:08
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

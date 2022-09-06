package com.mashibing.apidriver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2022/9/6 - 8:52
 */
@RestController
public class TestController {

	@GetMapping("/test")
	public String test() {
		return "test";
	}
}

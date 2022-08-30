package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.UserService;
import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 简单的数据可以在Controller
 * @author xcy
 * @date 2022/8/29 - 15:15
 */
@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/users")
	public ResponseResult getUser(HttpServletRequest request) {
		String accessToken = request.getHeader("Authorization");

		return userService.getUserByAccessToken(accessToken);
	}
}

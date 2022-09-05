package com.mashibing.apiboss.remote;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xcy
 * @date 2022/9/5 - 16:05
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

	/**
	 * 该方法的请求参数是以@RequestBody的方式，并且请求方式是POST请求，所以不需要引入feign-httpclient依赖
	 * @param driverUser
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/user")
	public ResponseResult addDriverUser(@RequestBody DriverUser driverUser);

	@RequestMapping(method = RequestMethod.PUT, value = "/user")
	public ResponseResult updateDriverUser(@RequestBody DriverUser driverUser);
}

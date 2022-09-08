package com.mashibing.apidriver.remote;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xcy
 * @date 2022/9/6 - 9:20
 */
@FeignClient("service-driver-user")
public interface ServiceDriverUserClient {

	@RequestMapping(method = RequestMethod.PUT, value = "/user")
	public ResponseResult updateUser(@RequestBody DriverUser driverUser);

	@RequestMapping(method = RequestMethod.GET, value = "/check-driver/{driverPhone}")
	public ResponseResult<DriverUserExistsResponse> selectUser(@PathVariable("driverPhone") String driverPhone);

}

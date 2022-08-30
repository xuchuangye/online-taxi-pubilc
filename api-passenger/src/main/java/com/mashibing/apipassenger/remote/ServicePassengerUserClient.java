package com.mashibing.apipassenger.remote;

import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xcy
 * @date 2022/8/25 - 15:46
 */
@FeignClient("service-passenger-user")
public interface ServicePassengerUserClient {

	@RequestMapping(method = RequestMethod.POST, value = "/user")
	public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO);

	/**
	 * 请求参数如果是以@RequestBody的方式，那么请求方式会从GET方式转换成POST方式
	 * @param phone
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/user/{phone}")
	public ResponseResult<PassengerUser> getUserByPhone(@PathVariable("phone") String phone);
}

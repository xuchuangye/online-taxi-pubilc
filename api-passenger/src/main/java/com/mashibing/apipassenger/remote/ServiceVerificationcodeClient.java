package com.mashibing.apipassenger.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xcy
 * @date 2022/8/23 - 8:54
 */
@FeignClient("service-verificationcode")
public interface ServiceVerificationcodeClient {

	//@GetMapping("/numberCode/6")
	@RequestMapping(method = RequestMethod.GET, value = "/numberCode/{size}")
	ResponseResult<NumberCodeResponse> getNumberCode(@PathVariable("size") int size);
}

package com.mashibing.apidriver.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xcy
 * @date 2022/9/8 - 8:45
 */
@FeignClient("service-verificationcode")
public interface ServiceVerificationCodeClient {

	@RequestMapping(method = RequestMethod.GET, value = "/numberCode/{size}")
	public ResponseResult<NumberCodeResponse> getVerificationCode(@PathVariable("size") int size);
}

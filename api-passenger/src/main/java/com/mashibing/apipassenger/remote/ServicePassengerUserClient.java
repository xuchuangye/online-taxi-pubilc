package com.mashibing.apipassenger.remote;

import com.mashibing.internalcommon.request.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import javafx.geometry.Pos;
import org.springframework.cloud.openfeign.FeignClient;
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
}

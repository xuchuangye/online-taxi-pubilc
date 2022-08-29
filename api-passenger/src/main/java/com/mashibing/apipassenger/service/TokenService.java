package com.mashibing.apipassenger.service;

import com.alibaba.nacos.common.utils.StringUtils;
import com.auth0.jwt.JWT;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.TokenConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.response.TokenResponse;
import com.mashibing.internalcommon.util.JwtUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xcy
 * @date 2022/8/29 - 9:20
 */
@Service
public class TokenService {
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	public ResponseResult refreshToken(String refreshTokenSrc) {
		//解析refreshToken
		TokenResult tokenResult = JwtUtils.checkToken(refreshTokenSrc);
		if (tokenResult == null) {
			return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getMessage());
		}
		//refreshToken的手机号以及身份标识
		String phone = tokenResult.getPhone();
		String identity = tokenResult.getIdentity();

		//生成refreshToken
		//生成refreshTokenKey
		String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstant.REFRESH_TOKEN_TYPE);

		//根据refreshTokenKey，获取Redis中refreshToken
		String refreshTokenRedis = stringRedisTemplate.opsForValue().get(refreshTokenKey);

		//校验refreshToken
		//判断从Redis中获取的refreshTokenRedis是否为空
		//或者
		//传入的refreshTokenSrc和从Redis中获取的refreshTokenRedis是否一样
		if (StringUtils.isBlank(refreshTokenRedis) || (!refreshTokenSrc.trim().equals(refreshTokenRedis.trim()))) {
			return ResponseResult.fail(CommonStatusEnum.TOKEN_ERROR.getCode(), CommonStatusEnum.TOKEN_ERROR.getMessage());
		}
		//生成双token
		String refreshToken = JwtUtils.generatorToken(phone, identity, TokenConstant.REFRESH_TOKEN_TYPE);
		String accessToken = JwtUtils.generatorToken(phone, identity, TokenConstant.ACCESS_TOKEN_TYPE);
		//生成accessTokenKey
		String accessTokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstant.ACCESS_TOKEN_TYPE);
		//将双token存储到Redis
		stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);
		stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

		//返回响应
		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setRefreshToken(refreshToken);
		tokenResponse.setAccessToken(accessToken);
		return ResponseResult.success(tokenResponse);
	}
}

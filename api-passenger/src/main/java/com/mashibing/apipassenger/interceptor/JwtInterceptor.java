package com.mashibing.apipassenger.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.util.JwtUtils;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author xcy
 * @date 2022/8/26 - 9:59
 */
public class JwtInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("Authorization");
		String resultString = "";
		boolean result = true;

		try {
			JwtUtils.parseToken(token);
		} catch (SignatureVerificationException e) {
			resultString = "token sign error";
			result = false;
		} catch (TokenExpiredException e) {
			resultString = "token time out";
			result = false;
		} catch (AlgorithmMismatchException e) {
			resultString = "token AlgorithmMismatchException";
			result = false;
		} catch (Exception e) {
			resultString = "token invalid";
			result = false;
		}
		if (!result) {
			PrintWriter writer = response.getWriter();
			writer.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
		}
		return result;
	}
}

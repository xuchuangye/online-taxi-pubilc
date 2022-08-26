package com.mashibing.internalcommon.dto;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xcy
 * @date 2022/8/22 - 18:29
 */
@Data
//调用链，可以返回对象继续调用后面的方法
@Accessors(chain = true)
public class ResponseResult<T> {

	/**
	 * 状态码
	 */
	private int code;
	/**
	 * 提示信息
	 */
	private String message;
	/**
	 * 响应数据
	 */
	private T data;


	public static <T> ResponseResult success() {
		return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode())
				.setMessage(CommonStatusEnum.SUCCESS.getMessage());
	}
	/**
	 * 成功响应的方法
	 *
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> ResponseResult success(T data) {
		return new ResponseResult().setCode(CommonStatusEnum.SUCCESS.getCode())
				.setMessage(CommonStatusEnum.SUCCESS.getMessage())
				.setData(data);
	}
	/**
	 * 失败，统一的失败处理
	 * @param data
	 * @param <T>
	 * @return
	 */
	public static <T> ResponseResult fail(T data) {
		return new ResponseResult().setData(data);
	}
	/**
	 * 失败，自定义失败，错误码和提示信息
	 * @param code
	 * @param message
	 * @return
	 */
	public static ResponseResult fail(int code, String message) {
		return new ResponseResult().setCode(code).setMessage(message);
	}
	/**
	 * 失败，自定义失败，错误码和提示信息，以及错误数据
	 * @param code
	 * @param message
	 * @param data
	 * @return
	 */
	public static ResponseResult fail(int code, String message, String data) {
		return new ResponseResult().setCode(code).setMessage(message).setData(data);
	}
}

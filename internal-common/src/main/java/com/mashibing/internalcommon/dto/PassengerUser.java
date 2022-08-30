package com.mashibing.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xcy
 * @date 2022/8/25 - 11:06
 */
@Data
public class PassengerUser {
	/**
	 * 乘客的ID
	 */
	private Long id;
	/**
	 * 乘客的注册时间
	 */
	private LocalDateTime gmtCreate;
	/**
	 * 乘客的更新时间
	 */
	private LocalDateTime gmtModified;
	/**
	 * 乘客的手机号
	 */
	private String passengerPhone;
	/**
	 * 乘客的姓名
	 */
	private String passengerName;
	/**
	 * 乘客的性别
	 */
	private Byte passengerGender;
	/**
	 * 乘客的状态
	 * 0：表示有效
	 * 1：表示无效
	 */
	private Byte state;
	/**
	 * 乘客的头像
	 */
	private String profilePhoto;
}

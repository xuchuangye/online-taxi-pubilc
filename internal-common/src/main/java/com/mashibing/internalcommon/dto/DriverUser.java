package com.mashibing.internalcommon.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author xcy
 * @date 2022/9/5 - 9:43
 */
@Data
public class DriverUser {
	private Integer id;
	/**
	 * 注册地行政区划代码
	 */
	private String address;
	/**
	 * 司机姓名
	 */
	private String driverName;
	/**
	 * 司机手机号
	 */
	private String driverPhone;
	/**
	 * 司机性别：1：男性，2：女性
	 */
	private Integer driverGender;
	/**
	 * 司机出生年月日
	 */
	private LocalDateTime driverBirthday;
	/**
	 * 司机民族
	 */
	private String driverNation;
	/**
	 * 司机通信地址
	 */
	private String driverContactAddress;
	/**
	 * 司机驾驶证号
	 */
	private String licenseId;
	/**
	 * 初次领取驾驶证日期
	 */
	private Date getDriverLicenseDate;
	/**
	 * 驾驶证有效期限起
	 */
	private Date driverLicenseOn;
	/**
	 * 驾驶证有效期限止
	 */
	private Date driverLicenseOff;
	/**
	 * 是否巡游出租汽车司机，1：是，0：否
	 */
	private Integer taxiDriver;
	/**
	 * 网络预约出租汽车司机资格证号
	 */
	private String certificateNo;
	/**
	 * 网络预约出租汽车司机发证机构
	 */
	private String networkCarIssueOrganization;
	/**
	 * 资格证发证日期
	 */
	private Date networkCarIssueDate;
	/**
	 * 初次领取资格证日期
	 */
	private Date getNetworkCarProofDate;
	/**
	 * 资格证有效起始日期
	 */
	private Date networkCarProofOn;
	/**
	 * 资格证有效截止日期
	 */
	private Date networkCarProofOff;
	/**
	 * 报备日期
	 */
	private Date registerDate;
	/**
	 * 服务类型，1：网络预约出租汽车，2：巡游出租汽车，3：私人小客车合乘
	 */
	private Integer commercialType;
	/**
	 * 司机合同（或协议）签署公司
	 */
	private String contractCompany;
	/**
	 * 合同（或协议）有效日期起
	 */
	private LocalDateTime contractOn;
	/**
	 * 合同（或协议）有效日期止
	 */
	private LocalDateTime contractOff;
	/**
	 * 司机状态，1：失效，0：有效
	 */
	private Integer state;
	/**
	 * 创建时间
	 */
	private LocalDateTime gmtCreate;
	/**
	 * 更新时间
	 */
	private LocalDateTime gmtModified;

}

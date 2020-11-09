package com.taotao.cloud.aftersale.biz.entity;

import com.alibaba.fastjson.JSON;
import com.taotao.cloud.data.jpa.entity.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

@Data
@SuperBuilder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tt_aftersale_flow_record")
@org.hibernate.annotations.Table(appliesTo = "tt_aftersale_flow_record", comment = "入金流水记录表")
public class FlowRecord extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 交易时间
	 */
	@ApiModelProperty(value = "交易时间")
	@NotNull(message = "交易时间不能为空")
	private Date tradeDate;

	/**
	 * 转出方code
	 */
	@ApiModelProperty(value = "转出方编码")
	@NotBlank(message = "转出方不能为空")
	@Size(max = 50, message = "转出方编码最大长度为50")
	@Column(length = 50)
	private String transferorCode;

	/**
	 * 转出方姓名
	 */
	@ApiModelProperty(value = "转出方姓名")
	@NotBlank(message = "转出方姓名不能为空")
	@Size(max = 32, message = "转出方姓名最大长度为32")
	@Column(length = 32)
	private String transferor;

	/**
	 * 转入方code
	 */
	@ApiModelProperty(value = "转入方编码", required = true)
	@NotBlank(message = "转入方编码不能为空")
	@Size(max = 50, message = "转入方编码最大长度为50")
	@Column(length = 50)
	private String transfereeCode;

	/**
	 * 转入方姓名
	 */
	@ApiModelProperty(value = "转入方姓名", required = true)
	@NotBlank(message = "转入方姓名不能为空")
	@Size(max = 32, message = "转入方姓名最大长度为32")
	@Column(length = 32)
	private String transferee;

	/**
	 * 交易金额
	 */
	@ApiModelProperty(value = "交易金额")
	@Min(value = 0, message = "交易金额小于零")
	@NotNull(message = "交易金额不能为空")
	private BigDecimal tradeAmount;

	/**
	 * 转入类型 （1--定时转入 2--提现驱动）
	 */
	@ApiModelProperty(value = "转入类型 (1--定时转入 2--提现驱动)")
	@NotNull(message = "转入类型不能为空")
	private Integer driveType;

}


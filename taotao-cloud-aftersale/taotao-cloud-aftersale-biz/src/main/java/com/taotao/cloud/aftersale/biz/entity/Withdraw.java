package com.taotao.cloud.aftersale.biz.entity;

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
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@SuperBuilder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_aftersale_withdraw")
@org.hibernate.annotations.Table(appliesTo = "t_aftersale_withdraw", comment = "提现申请表")
public class Withdraw extends BaseEntity {

	private static final long serialVersionUID = 6887296988458221221L;

	/**
	 * 申请单号
	 */
	@Column(name = "code", nullable = true, unique = true, columnDefinition = "varchar(32) not null comment '申请单号'")
	private String code;


	@ApiModelProperty(value = "公司ID -> 租户编码")
	private Long companyId;

	@ApiModelProperty(value = "商城ID -> 所属小程序")
	private Long mallId;

	@ApiModelProperty(value = "管家ID")
	private Long stewardId;

	@ApiModelProperty(value = "管家姓名")
	private String stewardName;

	@ApiModelProperty(value = "提现金额")
	private BigDecimal amount;

	@ApiModelProperty(value = "钱包余额")
	private BigDecimal balanceAmount;

	@ApiModelProperty(value = "账户变动历史编号")
	private String accountHistoryCode;

	/**
	 * 如果人工审核，则需有提现审核超时秒数
	 */
	@ApiModelProperty(value = "超时审核时间")
	private LocalDateTime expiredAuditTime;

	@ApiModelProperty(value = "审核时间")
	private LocalDateTime auditTime;

	@ApiModelProperty(value = "审核人ID")
	private Long auditorId;

	@ApiModelProperty(value = "打款时间")
	private LocalDateTime payTime;

	@ApiModelProperty(value = "有效打款单号")
	private String withdrawPayCode;

	@ApiModelProperty(value = "有效打款渠道单号")
	private String withdrawPayChannelCode;

	@ApiModelProperty(value = "打款成功时间")
	private LocalDateTime paySuccessTime;

	@ApiModelProperty(value = "领取时间")
	private LocalDateTime receiveTime;

	@ApiModelProperty(value = "退回时间")
	private LocalDateTime backTime;

	/**
	 * 申请单状态 枚举类->WithdrawStatusEnum
	 */
	@ApiModelProperty(value = "状态：-1-提交申请 0-待审核 1-审核通过 2-挂起 3-待领取 4-已领取 5-过期未领取")
	private Integer status;

	@ApiModelProperty(value = "所属项目")
	private String belongProject;

	@ApiModelProperty(value = "所属分公司")
	private String belongCompany;
}

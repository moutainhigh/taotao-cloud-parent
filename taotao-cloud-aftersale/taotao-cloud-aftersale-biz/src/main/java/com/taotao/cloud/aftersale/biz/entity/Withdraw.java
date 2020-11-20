package com.taotao.cloud.aftersale.biz.entity;

import com.taotao.cloud.data.jpa.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

/**
 * 提现申请表
 *
 * @author dengtao
 * @date 2020/11/13 09:46
 * @since v1.0
 */
@Data
@SuperBuilder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tt_withdraw")
@org.hibernate.annotations.Table(appliesTo = "tt_withdraw", comment = "提现申请表")
public class Withdraw extends BaseEntity {

	private static final long serialVersionUID = 6887296988458221221L;

	/**
	 * 申请单号
	 */
	@Column(name = "code", nullable = false, unique = true, columnDefinition = "varchar(32) not null comment '申请单号'")
	private String code;

	/**
	 * 公司ID
	 */
	@Column(name = "company_id", nullable = false, columnDefinition = "bigint not null comment '公司ID'")
	private Long companyId;

	/**
	 * 商城ID
	 */
	@Column(name = "mall_id", nullable = false, columnDefinition = "bigint not null comment '商城ID'")
	private Long mallId;

	/**
	 * 提现金额
	 */
	@Builder.Default
	@Column(name = "amount", nullable = false, columnDefinition = "decimal(10,2) not null default 0 comment '提现金额'")
	private BigDecimal amount = BigDecimal.ZERO;

	/**
	 * 钱包余额
	 */
	@Builder.Default
	@Column(name = "balance_amount", nullable = false, columnDefinition = "decimal(10,2) not null default 0 comment '钱包余额'")
	private BigDecimal balanceAmount = BigDecimal.ZERO;

}

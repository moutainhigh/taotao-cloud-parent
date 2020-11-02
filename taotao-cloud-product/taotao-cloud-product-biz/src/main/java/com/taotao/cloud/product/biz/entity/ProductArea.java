package com.taotao.cloud.product.biz.entity;


import com.taotao.cloud.data.jpa.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 商品销售范围表
 *
 * @author dengtao
 * @date 2020/4/30 16:04
 */
@Data
@SuperBuilder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
//@Entity
@Table(name = "product_area")
@org.hibernate.annotations.Table(appliesTo = "product_area", comment = "商品销售范围表")
public class ProductArea extends BaseEntity {

    private String regionJson;

    private int type;

}

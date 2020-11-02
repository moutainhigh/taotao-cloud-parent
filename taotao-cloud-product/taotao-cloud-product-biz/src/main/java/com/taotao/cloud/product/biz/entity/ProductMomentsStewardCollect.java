package com.taotao.cloud.product.biz.entity;


import com.taotao.cloud.data.jpa.entity.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author dengtao
 */
@Data
@SuperBuilder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
//@Entity
@Table(name = "product_moments_steward_collect")
@org.hibernate.annotations.Table(appliesTo = "product_moments_steward_collect", comment = "商品信息扩展表")
public class ProductMomentsStewardCollect extends BaseEntity {

    private Long stewardId;

    private Long momentsId;

    private LocalDateTime collectTime;

}

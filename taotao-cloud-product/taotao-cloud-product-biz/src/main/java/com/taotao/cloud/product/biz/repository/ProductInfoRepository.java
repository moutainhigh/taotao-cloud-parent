/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taotao.cloud.product.biz.repository;

import com.taotao.cloud.data.jpa.repository.BaseJpaRepository;
import com.taotao.cloud.product.biz.entity.ProductInfo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * 商品信息Repository
 *
 * @author dengtao
 * @date 2020/9/29 18:02
 * @since v1.0
 */
@Repository
public class ProductInfoRepository extends BaseJpaRepository<ProductInfo, Long> {
    public ProductInfoRepository(EntityManager em) {
        super(ProductInfo.class, em);
    }
}

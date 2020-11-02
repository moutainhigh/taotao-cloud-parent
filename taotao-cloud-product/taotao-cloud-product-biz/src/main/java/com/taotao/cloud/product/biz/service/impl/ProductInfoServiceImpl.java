/**
 * Project Name: my-projects
 * Package Name: com.taotao.cloud.order.biz.service.impl
 * Date: 2020/6/10 16:55
 * Author: dengtao
 */
package com.taotao.cloud.product.biz.service.impl;

import com.taotao.cloud.common.enums.ResultEnum;
import com.taotao.cloud.common.exception.BusinessException;
import com.taotao.cloud.common.utils.BeanUtil;
import com.taotao.cloud.product.api.dto.ProductDTO;
import com.taotao.cloud.product.biz.entity.ProductInfo;
import com.taotao.cloud.product.biz.repository.ProductInfoRepository;
import com.taotao.cloud.product.biz.service.IProductInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * <br>
 *
 * @author dengtao
 * @date 2020/6/10 16:55
 * @since v1.0
 */
@Service
@AllArgsConstructor
public class ProductInfoServiceImpl implements IProductInfoService {

    private final ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findProductInfoById(Long id) {
        Optional<ProductInfo> optionalProductInfo = productInfoRepository.findById(id);
        return optionalProductInfo.orElseThrow(() -> new BusinessException(ResultEnum.PRODUCT_NOT_EXIST));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ProductInfo saveProduct(ProductDTO productDTO) {
        ProductInfo productInfo = ProductInfo.builder().build();
        BeanUtil.copyIgnoredNull(productDTO, productInfo);
        return productInfoRepository.saveAndFlush(productInfo);
    }
}

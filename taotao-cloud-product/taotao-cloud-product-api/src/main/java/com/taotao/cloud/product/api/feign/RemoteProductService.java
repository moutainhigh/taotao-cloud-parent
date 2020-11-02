package com.taotao.cloud.product.api.feign;

import com.taotao.cloud.common.constant.ServiceNameConstant;
import com.taotao.cloud.core.model.Result;
import com.taotao.cloud.product.api.dto.ProductDTO;
import com.taotao.cloud.product.api.feign.fallback.RemoteProductFallbackImpl;
import com.taotao.cloud.product.api.vo.ProductVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 远程调用订单模块
 *
 * @author dengtao
 * @date 2020/5/2 16:42
 */
@FeignClient(contextId = "remoteProductService", value = ServiceNameConstant.TAOTAO_CLOUD_PRODUCT_CENTER, fallbackFactory = RemoteProductFallbackImpl.class)
public interface RemoteProductService {

    @GetMapping(value = "/product/id/{id}")
    Result<ProductVO> findProductInfoById(@PathVariable("id") Long id);

    @PostMapping(value = "/product")
    Result<ProductVO> saveProduct(@RequestBody ProductDTO productDTO);
}


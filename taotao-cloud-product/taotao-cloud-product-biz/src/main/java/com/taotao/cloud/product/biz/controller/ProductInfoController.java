package com.taotao.cloud.product.biz.controller;

import com.taotao.cloud.core.model.Result;
import com.taotao.cloud.log.annotation.SysOperateLog;
import com.taotao.cloud.product.api.dto.ProductDTO;
import com.taotao.cloud.product.api.vo.ProductVO;
import com.taotao.cloud.product.biz.entity.ProductInfo;
import com.taotao.cloud.product.biz.service.IProductInfoService;
import com.taotao.cloud.product.biz.utils.ProductUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 商品管理API
 *
 * @author dengtao
 * @date 2020/4/30 11:03
 */
@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/product")
@Api(value = "商品管理API", tags = {"商品管理API"})
public class ProductInfoController {

    private final IProductInfoService productInfoService;

    @ApiOperation("根据id查询商品信息")
    @SysOperateLog(description = "根据id查询商品信息")
    //@PreAuthorize("hasAuthority('sys:user:view')")
    @GetMapping(value = "/id/{id}")
    Result<ProductVO> findProductInfoById(@PathVariable("id") Long id) {
        ProductInfo productInfo = productInfoService.findProductInfoById(id);
        return Result.succeed(ProductUtil.copy(productInfo));
    }

    @ApiOperation("添加商品信息")
    @PostMapping
    @SysOperateLog(description = "添加商品信息")
    Result<ProductVO> saveProduct(@Validated @RequestBody ProductDTO productDTO) {
        ProductInfo productInfo = productInfoService.saveProduct(productDTO);
        return Result.succeed(ProductUtil.copy(productInfo));
    }

}


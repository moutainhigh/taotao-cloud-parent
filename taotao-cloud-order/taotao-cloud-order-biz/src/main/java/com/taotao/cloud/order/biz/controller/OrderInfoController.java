package com.taotao.cloud.order.biz.controller;

import com.taotao.cloud.core.model.Result;
import com.taotao.cloud.log.annotation.SysOperateLog;
import com.taotao.cloud.order.api.dto.OrderDTO;
import com.taotao.cloud.order.api.vo.OrderVO;
import com.taotao.cloud.order.biz.entity.OrderInfo;
import com.taotao.cloud.order.biz.service.IOrderInfoService;
import com.taotao.cloud.order.biz.utils.OrderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 订单管理API
 *
 * @author dengtao
 * @date 2020/4/30 11:03
 */
@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/order")
@Api(value = "订单管理API", tags = {"订单管理API"})
public class OrderInfoController {

    private final IOrderInfoService orderInfoService;

    @ApiOperation("获取订单信息")
    @GetMapping("/info/{code}")
    @SysOperateLog(description = "获取订单信息")
    //@PreAuthorize("hasAuthority('order:info:code')")
    public Result<OrderVO> findOrderInfoByCode(HttpServletRequest request, @PathVariable("code") String code) {
        OrderInfo orderInfo = orderInfoService.findOrderInfoByCode(code);
        return Result.succeed(OrderUtil.copy(orderInfo));
    }

    @ApiOperation("添加订单信息")
    @PostMapping
    @SysOperateLog(description = "添加订单信息")
    Result<OrderVO> saveOrder(@Validated @RequestBody OrderDTO orderDTO) {
        OrderInfo orderInfo = orderInfoService.saveOrder(orderDTO);
        return Result.succeed(OrderUtil.copy(orderInfo));
    }

}


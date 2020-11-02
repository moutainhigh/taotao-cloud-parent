package com.taotao.cloud.uc.biz.controller;

import com.taotao.cloud.uc.biz.service.ISysDeptService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 部门管理API
 *
 * @author dengtao
 * @date 2020-10-16 15:54:05
 * @since 1.0
 */
@Validated
@RestController
@RequestMapping("/dept")
@Api(value = "部门管理API", tags = {"部门管理API"})
@AllArgsConstructor
public class SysDeptController {
    private final ISysDeptService deptService;
}

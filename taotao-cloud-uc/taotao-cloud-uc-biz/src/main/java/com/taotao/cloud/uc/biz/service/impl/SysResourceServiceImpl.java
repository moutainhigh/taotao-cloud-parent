package com.taotao.cloud.uc.biz.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.taotao.cloud.common.constant.CommonConstant;
import com.taotao.cloud.common.enums.ResourceTypeEnum;
import com.taotao.cloud.common.enums.ResultEnum;
import com.taotao.cloud.common.exception.BusinessException;
import com.taotao.cloud.common.utils.BeanUtil;
import com.taotao.cloud.common.utils.LogUtil;
import com.taotao.cloud.core.model.Result;
import com.taotao.cloud.core.model.SecurityUser;
import com.taotao.cloud.core.utils.SecurityUtil;
import com.taotao.cloud.order.api.dto.OrderDTO;
import com.taotao.cloud.order.api.feign.RemoteOrderService;
import com.taotao.cloud.order.api.vo.OrderVO;
import com.taotao.cloud.product.api.dto.ProductDTO;
import com.taotao.cloud.product.api.feign.RemoteProductService;
import com.taotao.cloud.uc.api.dto.resource.ResourceDTO;
import com.taotao.cloud.uc.api.query.resource.ResourcePageQuery;
import com.taotao.cloud.uc.api.vo.resource.ResourceTree;
import com.taotao.cloud.uc.api.vo.resource.ResourceVO;
import com.taotao.cloud.uc.biz.entity.QSysResource;
import com.taotao.cloud.uc.biz.entity.SysResource;
import com.taotao.cloud.uc.biz.entity.SysRole;
import com.taotao.cloud.uc.biz.repository.SysResourceRepository;
import com.taotao.cloud.uc.biz.service.ISysResourceService;
import com.taotao.cloud.uc.biz.service.ISysRoleService;
import com.taotao.cloud.uc.biz.utils.TreeUtil;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 资源表服务实现类
 *
 * @author dengtao
 * @date 2020-10-16 16:23:05
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class SysResourceServiceImpl implements ISysResourceService {
    private final SysResourceRepository sysResourceRepository;
    private final ISysRoleService sysRoleService;

    private final RemoteOrderService remoteOrderService;
    private final RemoteProductService remoteProductService;

    private final static QSysResource SYS_RESOURCE = QSysResource.sysResource;
    private List<SysResource> resourceList;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveResource(ResourceDTO resourceDTO) {
        SysResource sysResource = SysResource.builder().build();
        BeanUtil.copyIgnoredNull(resourceDTO, sysResource);
        sysResourceRepository.saveAndFlush(sysResource);
        return true;
    }

    @Override
    public Boolean deleteResource(Long id) {
        sysResourceRepository.deleteById(id);
        // 需要级联删除 子资源

        //
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateResource(Long id, ResourceDTO resourceDTO) {
        SysResource sysResource = findResourceById(id);
        BeanUtil.copyIgnoredNull(resourceDTO, sysResource);
        sysResourceRepository.saveAndFlush(sysResource);
        return true;
    }

    @Override
    public SysResource findResourceById(Long id) {
        Optional<SysResource> optionalSysResource = sysResourceRepository.findById(id);
        return optionalSysResource.orElseThrow(() -> new BusinessException(ResultEnum.RESOURCE_NOT_EXIST));
    }

    @Override
    public Page<SysResource> findResourcePage(Pageable pageable, ResourcePageQuery resourceQuery) {
        BooleanExpression expression = SYS_RESOURCE.delFlag.eq(false);
        OrderSpecifier<LocalDateTime> desc = SYS_RESOURCE.createTime.desc();
        return sysResourceRepository.findAll(expression, pageable, desc);
    }

    @Override
    public List<SysResource> findAllResources() {
        Result<OrderVO> orderInfoByCode = remoteOrderService.findOrderInfoByCode("111111");
        SecurityUser user = SecurityUtil.getUser();
        return sysResourceRepository.findAll();
    }

    @Override
    public List<SysResource> findResourceByRoleIds(Set<Long> roleIds) {
        List<SysResource> sysResources = sysResourceRepository.findResourceByRoleIds(roleIds);
        return sysResources.stream().collect(Collectors.collectingAndThen(
                Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(SysResource::getId))), ArrayList::new));
    }

    @Override
    public List<SysResource> findResourceByCodes(Set<String> codes) {
        List<SysRole> sysRoles = sysRoleService.findRoleByCodes(codes);
        if (CollUtil.isEmpty(sysRoles)) {
            throw new BusinessException("未查询到角色信息");
        }
        List<Long> roleIds = sysRoles.stream().map(SysRole::getId).collect(Collectors.toList());
        return findResourceByRoleIds(new HashSet<>(roleIds));
    }

    @Override
    public List<SysResource> findResourceByParentId(Long parentId) {
        List<SysResource> resources = sysResourceRepository.findResourceByParentId(parentId);
        recursiveFind(resources);
        List<SysResource> result = resourceList;
        resourceList = Collections.synchronizedList(new ArrayList<>());
        return result;
    }

    /**
     * 递归查询
     *
     * @param resources
     * @return java.util.List<com.taotao.cloud.uc.biz.entity.SysResource>
     * @author dengtao
     * @date 2020/10/21 13:44
     * @since v1.0
     */
    private void recursiveFind(List<SysResource> resources) {
        resourceList.addAll(resources);
        for (SysResource resource : resources) {
            Long parentId = resource.getId();
            if (Objects.nonNull(parentId) && parentId != 0) {
                recursiveFind(sysResourceRepository.findResourceByParentId(parentId));
            }
        }
    }

    @Override
    public List<ResourceTree> findResourceTree(boolean lazy, Long parentId) {
        if (!lazy) {
            List<SysResource> resources = findAllResources();
            return TreeUtil.buildTree(resources, CommonConstant.RESOURCE_TREE_ROOT_ID);
        }

        Long parent = parentId == null ? CommonConstant.RESOURCE_TREE_ROOT_ID : parentId;
        List<SysResource> resources = findResourceByParentId(parent);
        return TreeUtil.buildTree(resources, parent);
    }

    @Override
    public List<ResourceTree> findCurrentUserResourceTree(List<ResourceVO> resourceVOList, Long parentId) {
        List<ResourceTree> menuTreeList = resourceVOList.stream().filter(vo -> ResourceTypeEnum.LEFT_MENU.getValue().equals(vo.getType()))
                .map(ResourceTree::new).sorted(Comparator.comparingInt(ResourceTree::getSort)).collect(Collectors.toList());
        Long parent = parentId == null ? CommonConstant.RESOURCE_TREE_ROOT_ID : parentId;
        return TreeUtil.build(menuTreeList, parent);
    }

    @Override
    @GlobalTransactional(name = "fsp-create-order", rollbackFor = Exception.class)
    public Boolean testSeata() {
        LogUtil.info("1.添加资源信息");
        ResourceDTO resourceDTO = ResourceDTO.builder()
                .name("资源三")
                .type((byte) 1)
                .parentId(0L)
                .sortNum(2)
                .build();
        saveResource(resourceDTO);

        LogUtil.info("2.远程添加商品信息");
        ProductDTO productDTO = ProductDTO.builder()
                .name("机器人三")
                .supplierId(1L)
                .picId(1L)
                .videoId(1L)
                .detailPicId(1L)
                .firstPicId(1L)
                .posterPicId(1L)
                .remark("备注")
                .status(1)
                .build();
        remoteProductService.saveProduct(productDTO);

        LogUtil.info("3.远程添加订单信息");
        OrderDTO orderDTO = OrderDTO.builder()
                .memberId(2L)
                .code("33333")
                .amount(BigDecimal.ZERO)
                .mainStatus(1)
                .childStatus(1)
                .receiverName("dengtao")
                .receiverPhone("15730445330")
                .receiverAddressJson("sjdlasjdfljsldf")
                .build();
        Result<OrderVO> orderVOResult = remoteOrderService.saveOrder(orderDTO);
        return true;
    }
}

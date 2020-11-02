package com.taotao.cloud.uc.biz.service;

import com.taotao.cloud.uc.api.dto.user.RestPasswordUserDTO;
import com.taotao.cloud.uc.api.dto.user.UserDTO;
import com.taotao.cloud.uc.api.dto.user.UserRoleDTO;
import com.taotao.cloud.uc.api.query.user.UserPageQuery;
import com.taotao.cloud.uc.api.query.user.UserQuery;
import com.taotao.cloud.uc.api.vo.user.AddUserVO;
import com.taotao.cloud.uc.api.vo.user.UserVO;
import com.taotao.cloud.uc.biz.entity.SysUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 用户表 服务类
 *
 * @author dengtao
 * @date 2020/4/30 13:20
 */
public interface ISysUserService {
    /**
     * 添加用户
     *
     * @param userDto userDto
     * @author dengtao
     * @date 2020/9/30 13:39
     * @since v1.0
     */
    AddUserVO saveUser(UserDTO userDto);

    /**
     * 更新用户
     *
     * @param id      id
     * @param userDTO userDTO
     * @author dengtao
     * @date 2020/9/30 14:01
     * @since v1.0
     */
    Boolean updateUser(Long id, UserDTO userDTO);

    /**
     * 根据用户id删除用户
     *
     * @param id id
     * @author dengtao
     * @date 2020/9/30 14:07
     * @since v1.0
     */
    Boolean removeUser(Long id);

    /**
     * 查询用户集合
     *
     * @param userQuery userListQuery
     * @author dengtao
     * @date 2020/9/30 14:10
     * @since v1.0
     */
    Page<SysUser> findUserPage(Pageable page, UserPageQuery userQuery);

    /**
     * 重置密码
     *
     * @param restPasswordDTO restPasswordDTO
     * @author dengtao
     * @date 2020/9/30 14:22
     * @since v1.0
     */
    Boolean restPass(Long id, RestPasswordUserDTO restPasswordDTO);

    /**
     * 根据用户id查询用户信息
     *
     * @param userId userId
     * @author dengtao
     * @date 2020/9/30 14:36
     * @since v1.0
     */
    UserVO findUserInfoById(Long userId);

    /**
     * 查询用户集合
     *
     * @param userQuery userQuery
     * @return java.util.List<com.taotao.cloud.uc.api.vo.user.SysUserVO>
     * @author dengtao
     * @date 2020/10/14 21:40
     * @since v1.0
     */
    List<SysUser> findUserList(UserQuery userQuery);

    /**
     * 更新角色信息
     *
     * @param userRoleDTO
     * @return java.lang.Boolean
     * @author dengtao
     * @date 2020/10/21 09:24
     * @since v1.0
     */
    Boolean saveUserRoles(UserRoleDTO userRoleDTO);

    /**
     * 根据username获取用户信息
     *
     * @param username
     * @return com.taotao.cloud.uc.api.vo.user.UserVO
     * @author dengtao
     * @date 2020/10/21 15:03
     * @since v1.0
     */
    UserVO findUserInfoByUsername(String username);
}

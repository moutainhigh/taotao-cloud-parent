package com.taotao.cloud.uc.biz.service.impl;

import cn.hutool.core.util.StrUtil;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.taotao.cloud.common.enums.ResultEnum;
import com.taotao.cloud.common.exception.BusinessException;
import com.taotao.cloud.common.utils.BeanUtil;
import com.taotao.cloud.core.utils.AuthUtil;
import com.taotao.cloud.uc.api.dto.user.RestPasswordUserDTO;
import com.taotao.cloud.uc.api.dto.user.UserDTO;
import com.taotao.cloud.uc.api.dto.user.UserRoleDTO;
import com.taotao.cloud.uc.api.query.user.UserPageQuery;
import com.taotao.cloud.uc.api.query.user.UserQuery;
import com.taotao.cloud.uc.api.vo.user.AddUserVO;
import com.taotao.cloud.uc.api.vo.user.UserVO;
import com.taotao.cloud.uc.biz.entity.QSysUser;
import com.taotao.cloud.uc.biz.entity.SysUser;
import com.taotao.cloud.uc.biz.repository.SysUserRepository;
import com.taotao.cloud.uc.biz.service.ISysUserRoleService;
import com.taotao.cloud.uc.biz.service.ISysUserService;
import com.taotao.cloud.uc.biz.utils.SysUserUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 用户表 服务实现类
 *
 * @author dengtao
 * @date 2020/4/30 13:22
 */
@Service
@AllArgsConstructor
public class SysUserServiceImpl implements ISysUserService {
    private final SysUserRepository sysUserRepository;
    private final ISysUserRoleService sysUserRoleService;

    private final static QSysUser SYS_USER = QSysUser.sysUser;

    private final static String DEFAULT_PASSWORD = "123456";
    private final static String DEFAULT_USERNAME = "admin";

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AddUserVO saveUser(UserDTO userDto) {
        String phone = userDto.getPhone();
        BooleanExpression phonePredicate = SYS_USER.phone.eq(phone);
        Boolean exists = sysUserRepository.exists(phonePredicate);
        if (exists) {
            throw new BusinessException(ResultEnum.USER_PHONE_EXISTS_ERROR);
        }
        String nickname = userDto.getNickname();
        if (StrUtil.isBlank(nickname)) {
            userDto.setNickname(DEFAULT_USERNAME);
        }
        String username = userDto.getUsername();
        if (StrUtil.isBlank(username)) {
            userDto.setUsername(DEFAULT_USERNAME);
        }

        SysUser sysUser = new SysUser();
        BeanUtil.copyIgnoredNull(userDto, sysUser);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        sysUser.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        SysUser save = sysUserRepository.save(sysUser);

        return AddUserVO.builder()
                .phone(save.getPhone())
                .password(DEFAULT_PASSWORD)
                .username(save.getUsername())
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateUser(Long id, UserDTO userDTO) {
        Optional<SysUser> optionalSysUser = sysUserRepository.findById(id);
        SysUser sysUser = optionalSysUser.orElseThrow(() -> new BusinessException(ResultEnum.USER_NOT_EXIST));

        BeanUtil.copyIgnoredNull(userDTO, sysUser);
        sysUserRepository.save(sysUser);

        // 此处修改用户角色
        // userRoleService.remove(Wrappers.<SysUserRole>lambdaQuery().eq(SysUserRole::getId, sysUser.getId()));
        // List<SysUserRole> userRoles = userAddDto.getRoleList().stream().map(item -> {
        //     SysUserRole sysUserRole = new SysUserRole();
        //     sysUserRole.setRoleId(item);
        //     sysUserRole.setUserId(sysUser.getId());
        //     return sysUserRole;
        // }).collect(Collectors.toList());
        //
        // return userRoleService.saveBatch(userRoles);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeUser(Long id) {
        sysUserRepository.deleteById(id);
        return true;
    }

    @Override
    public Page<SysUser> findUserPage(Pageable page, UserPageQuery userQuery) {
        BooleanExpression predicate = SYS_USER.delFlag.eq(false);
        Optional.ofNullable(userQuery.getNickname())
                .ifPresent(nickname -> predicate.and(SYS_USER.nickname.like(nickname)));
        Optional.ofNullable(userQuery.getUsername())
                .ifPresent(username -> predicate.and(SYS_USER.username.like(username)));
        Optional.ofNullable(userQuery.getPhone())
                .ifPresent(phone -> predicate.and(SYS_USER.phone.eq(phone)));
        Optional.ofNullable(userQuery.getEmail())
                .ifPresent(email -> predicate.and(SYS_USER.email.eq(email)));
        Optional.ofNullable(userQuery.getDeptId())
                .ifPresent(deptId -> predicate.and(SYS_USER.deptId.eq(deptId)));
        Optional.ofNullable(userQuery.getJobId())
                .ifPresent(jobId -> predicate.and(SYS_USER.jobId.eq(jobId)));

        OrderSpecifier<LocalDateTime> createDatetimeDesc = SYS_USER.createTime.desc();
        return sysUserRepository.findAll(predicate, page, createDatetimeDesc);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean restPass(Long id, RestPasswordUserDTO restPasswordDTO) {
        String restPasswordPhone = restPasswordDTO.getPhone();
        Optional<SysUser> optionalSysUser = sysUserRepository.findById(id);
        SysUser sysUser = optionalSysUser.orElseThrow(() -> new BusinessException(ResultEnum.USER_NOT_EXIST));

        String phone = sysUser.getPhone();
        if (!Objects.equals(restPasswordPhone, phone)) {
            throw new BusinessException(ResultEnum.USER_PHONE_INCONSISTENT_ERROR);
        }
        BCryptPasswordEncoder passwordEncoder = AuthUtil.getPasswordEncoder();

        String oldPassword = restPasswordDTO.getOldPassword();
        String password = sysUser.getPassword();
        if (!AuthUtil.validatePass(oldPassword, password)) {
            throw new BusinessException(ResultEnum.USERNAME_OR_PASSWORD_ERROR);
        }

        String newPassword = restPasswordDTO.getNewPassword();
        return sysUserRepository.updatePassword(id, passwordEncoder.encode(newPassword));
    }

    @Override
    public UserVO findUserInfoById(Long userId) {
        Optional<SysUser> optionalSysUser = sysUserRepository.findById(userId);
        SysUser sysUser = optionalSysUser.orElseThrow(() -> new BusinessException(ResultEnum.USER_NOT_EXIST));
        return SysUserUtil.copy(sysUser);
    }

    @Override
    public UserVO findUserInfoByUsername(String username) {
        BooleanExpression expression = SYS_USER.username.eq(username);
        SysUser sysUser = sysUserRepository.fetchOne(expression);
        return SysUserUtil.copy(sysUser);
    }

    @Override
    public List<SysUser> findUserList(UserQuery userQuery) {
        BooleanExpression predicate = SYS_USER.delFlag.eq(false);
        Optional.ofNullable(userQuery.getNickname())
                .ifPresent(nickname -> predicate.and(SYS_USER.nickname.like(nickname)));
        Optional.ofNullable(userQuery.getUsername())
                .ifPresent(username -> predicate.and(SYS_USER.username.like(username)));
        Optional.ofNullable(userQuery.getPhone())
                .ifPresent(phone -> predicate.and(SYS_USER.phone.eq(phone)));
        Optional.ofNullable(userQuery.getEmail())
                .ifPresent(email -> predicate.and(SYS_USER.email.eq(email)));
        Optional.ofNullable(userQuery.getType())
                .ifPresent(type -> predicate.and(SYS_USER.type.eq(type)));
        Optional.ofNullable(userQuery.getSex())
                .ifPresent(sex -> predicate.and(SYS_USER.sex.eq(sex)));
        Optional.ofNullable(userQuery.getDeptId())
                .ifPresent(deptId -> predicate.and(SYS_USER.deptId.eq(deptId)));
        Optional.ofNullable(userQuery.getJobId())
                .ifPresent(jobId -> predicate.and(SYS_USER.jobId.eq(jobId)));
        return sysUserRepository.fetch(predicate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveUserRoles(UserRoleDTO userRoleDTO) {
        Long userId = userRoleDTO.getUserId();
        findUserInfoById(userId);
        return sysUserRoleService.saveUserRoles(userId, userRoleDTO.getRoleIds());
    }


//    @Override
//    public boolean doPostSignUp(SysUser user) {
// 进行账号校验
//        SysUser sysUser = findSecurityUserByUser(new SysUser().setUsername(user.getUsername()));
//        if (ObjectUtil.isNull(sysUser)) {
//            throw new BaseException("账号不存在");
//        }
//        Integer userId = sysUser.getId();
//        try {
//            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername()去验证用户名和密码，
//            // 如果正确，则存储该用户名密码到security 的 context中
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
//        } catch (Exception e) {
//            if (e instanceof BadCredentialsException) {
//                throw new BaseException("用户名或密码错误", 402);
//            } else if (e instanceof DisabledException) {
//                throw new BaseException("账户被禁用", 402);
//            } else if (e instanceof AccountExpiredException) {
//                throw new BaseException("账户过期无法验证", 402);
//            } else {
//                throw new BaseException("账户被锁定,无法登录", 402);
//            }
//        }
//        //将业务系统的用户与社交用户绑定
//        socialRedisHelper.doPostSignUp(user.getKey(), userId);
//        return true;
//    }

}

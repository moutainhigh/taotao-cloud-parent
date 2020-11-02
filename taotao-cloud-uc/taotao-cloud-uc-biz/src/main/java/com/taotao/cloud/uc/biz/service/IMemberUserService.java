package com.taotao.cloud.uc.biz.service;

import com.taotao.cloud.uc.api.dto.member.MemberUserDTO;
import com.taotao.cloud.uc.api.query.member.MemberQuery;
import com.taotao.cloud.uc.biz.entity.MemberUser;

/**
 * 会员(c端用户)表服务接口
 *
 * @author dengtao
 * @date 2020-10-16 16:23:49
 * @since 1.0
 */
public interface IMemberUserService {

    /**
     * 查询会员是否已(注册)存在
     *
     * @param memberQuery memberQuery
     * @return com.taotao.cloud.uc.biz.entity.MemberUser
     * @author dengtao
     * @date 2020/10/16 17:58
     * @since v1.0
     */
    Boolean existMember(MemberQuery memberQuery);

    /**
     * 注册新会员用户
     *
     * @param memberDTO memberDTO
     * @return java.lang.Boolean
     * @author dengtao
     * @date 2020/10/19 09:01
     * @since v1.0
     */
    MemberUser registerUser(MemberUserDTO memberDTO);

    /**
     * 查询会员用户
     *
     * @param memberQuery memberQuery
     * @return com.taotao.cloud.uc.biz.entity.MemberUser
     * @author dengtao
     * @date 2020/10/19 09:15
     * @since v1.0
     */
    MemberUser findMember(String nicknameOrUserNameOrPhoneOrEmail);
}

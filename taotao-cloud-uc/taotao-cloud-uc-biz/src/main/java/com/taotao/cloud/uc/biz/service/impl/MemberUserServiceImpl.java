package com.taotao.cloud.uc.biz.service.impl;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.taotao.cloud.common.enums.ResultEnum;
import com.taotao.cloud.common.exception.BusinessException;
import com.taotao.cloud.common.utils.AddrUtil;
import com.taotao.cloud.core.utils.AuthUtil;
import com.taotao.cloud.uc.api.dto.member.MemberUserDTO;
import com.taotao.cloud.uc.api.query.member.MemberQuery;
import com.taotao.cloud.uc.biz.entity.MemberUser;
import com.taotao.cloud.uc.biz.entity.QMemberUser;
import com.taotao.cloud.uc.biz.repository.MemberUserRepository;
import com.taotao.cloud.uc.biz.service.IMemberUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 会员(c端用户)表服务实现类
 *
 * @author dengtao
 * @date 2020-10-16 16:23:49
 * @since 1.0
 */
@Service
@AllArgsConstructor
public class MemberUserServiceImpl implements IMemberUserService {
    private final MemberUserRepository memberUserRepository;

    private static final QMemberUser MEMBER_USER = QMemberUser.memberUser;

    @Override
    public Boolean existMember(MemberQuery memberQuery) {
        BooleanExpression predicate = MEMBER_USER.delFlag.eq(false);

        Optional.ofNullable(memberQuery.getNickname())
                .ifPresent(nickname -> predicate.and(MEMBER_USER.nickname.eq(nickname)));
        Optional.ofNullable(memberQuery.getPhone())
                .ifPresent(phone -> predicate.and(MEMBER_USER.phone.eq(phone)));
        Optional.ofNullable(memberQuery.getUsername())
                .ifPresent(username -> predicate.and(MEMBER_USER.username.eq(username)));
        Optional.ofNullable(memberQuery.getEmail())
                .ifPresent(email -> predicate.and(MEMBER_USER.email.eq(email)));

        return memberUserRepository.exists(predicate);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MemberUser registerUser(MemberUserDTO memberDTO) {
        String nickname = memberDTO.getNickname();
        MemberQuery nicknameQuery = MemberQuery.builder().nickname(nickname).build();
        if (existMember(nicknameQuery)) {
            throw new BusinessException(ResultEnum.MEMBER_NICKNAME_EXIST);
        }
        String phone = memberDTO.getPhone();
        MemberQuery phoneQuery = MemberQuery.builder().phone(phone).build();
        if (existMember(phoneQuery)) {
            throw new BusinessException(ResultEnum.MEMBER_PHONE_EXIST);
        }
        HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest();
        BCryptPasswordEncoder passwordEncoder = AuthUtil.getPasswordEncoder();
        MemberUser member = MemberUser.builder()
                .nickname(nickname)
                .phone(phone)
                .password(passwordEncoder.encode(memberDTO.getPassword()))
                .createIp(AddrUtil.getRemoteAddr(request))
                .build();
        return memberUserRepository.saveAndFlush(member);
    }

    @Override
    public MemberUser findMember(String nicknameOrUserNameOrPhoneOrEmail) {
        BooleanExpression predicate = MEMBER_USER.delFlag.eq(false);
        predicate.eq(MEMBER_USER.username.eq(nicknameOrUserNameOrPhoneOrEmail))
                .or(MEMBER_USER.nickname.eq(nicknameOrUserNameOrPhoneOrEmail))
                .or(MEMBER_USER.phone.eq(nicknameOrUserNameOrPhoneOrEmail))
                .or(MEMBER_USER.email.eq(nicknameOrUserNameOrPhoneOrEmail));
        return memberUserRepository.fetchOne(predicate);
    }

}

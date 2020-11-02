package com.taotao.cloud.uc.biz.repository;

import com.taotao.cloud.data.jpa.repository.BaseJpaRepository;
import com.taotao.cloud.uc.biz.entity.MemberUser;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

/**
 * 会员(c端用户)表Repository
 *
 * @author dengtao
 * @date 2020/9/29 18:02
 * @since v1.0
 */
@Repository
public class MemberUserRepository extends BaseJpaRepository<MemberUser, Long> {
    public MemberUserRepository(EntityManager em) {
        super(MemberUser.class, em);
    }
}

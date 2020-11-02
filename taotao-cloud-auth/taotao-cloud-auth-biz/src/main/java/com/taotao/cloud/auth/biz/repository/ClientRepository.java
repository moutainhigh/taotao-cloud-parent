/*
 * Copyright 2017-2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taotao.cloud.auth.biz.repository;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.taotao.cloud.auth.api.query.ClientPageQuery;
import com.taotao.cloud.auth.biz.entity.Client;
import com.taotao.cloud.auth.biz.entity.QClient;
import com.taotao.cloud.common.enums.DelFlagEnum;
import com.taotao.cloud.data.jpa.repository.BaseJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.Optional;

/**
 * 客户端Repository
 *
 * @author dengtao
 * @date 2020/9/29 18:02
 * @since v1.0
 */
@Repository
public class ClientRepository extends BaseJpaRepository<Client, Long> {
    public ClientRepository(EntityManager em) {
        super(Client.class, em);
    }

    private final static QClient CLIENT = QClient.client;

    public Page<Client> getPage(ClientPageQuery clientPageQuery) {
        Pageable page = PageRequest.of(clientPageQuery.getCurrentPage(), clientPageQuery.getPageSize());

        BooleanExpression predicate = CLIENT.delFlag.eq(false);
        Optional.ofNullable(clientPageQuery.getClientId())
                .ifPresent(clientId -> predicate.and(CLIENT.clientId.eq(clientId)));
        Optional.ofNullable(clientPageQuery.getResourceIds())
                .ifPresent(resourceIds -> predicate.and(CLIENT.resourceIds.eq(resourceIds)));
        Optional.ofNullable(clientPageQuery.getClientSecret())
                .ifPresent(clientSecret -> predicate.and(CLIENT.clientSecret.eq(clientSecret)));
        Optional.ofNullable(clientPageQuery.getClientSecretStr())
                .ifPresent(clientSecretStr -> predicate.and(CLIENT.clientSecretStr.eq(clientSecretStr)));
        Optional.ofNullable(clientPageQuery.getScope())
                .ifPresent(scope -> predicate.and(CLIENT.scope.eq(scope)));
        Optional.ofNullable(clientPageQuery.getAuthorizedGrantTypes())
                .ifPresent(authorizedGrantTypes -> predicate.and(CLIENT.authorizedGrantTypes.eq(authorizedGrantTypes)));
        Optional.ofNullable(clientPageQuery.getWebServerRedirectUri())
                .ifPresent(webServerRedirectUri -> predicate.and(CLIENT.webServerRedirectUri.eq(webServerRedirectUri)));
        Optional.ofNullable(clientPageQuery.getAuthorities())
                .ifPresent(webServerRedirectUri -> predicate.and(CLIENT.authorities.eq(webServerRedirectUri)));
        Optional.ofNullable(clientPageQuery.getAccessTokenValiditySeconds())
                .ifPresent(accessTokenValidity -> predicate.and(CLIENT.accessTokenValiditySeconds.eq(accessTokenValidity)));
        Optional.ofNullable(clientPageQuery.getRefreshTokenValiditySeconds())
                .ifPresent(refreshTokenValidity -> predicate.and(CLIENT.refreshTokenValiditySeconds.eq(refreshTokenValidity)));
        Optional.ofNullable(clientPageQuery.getAdditionalInformation())
                .ifPresent(additionalInformation -> predicate.and(CLIENT.additionalInformation.eq(additionalInformation)));
        Optional.ofNullable(clientPageQuery.getAutoapprove())
                .ifPresent(autoapprove -> predicate.and(CLIENT.autoapprove.eq(autoapprove)));

        OrderSpecifier<LocalDateTime> createTimeDesc = CLIENT.createTime.desc();
        return findAll(predicate, page, createTimeDesc);
    }

    public Optional<Client> getByClientId(String clientId) {
        Client client = jpaQueryFactory.selectFrom(CLIENT)
                .where(CLIENT.clientId.eq(clientId))
                .fetchOne();
        return Optional.ofNullable(client);
    }

    public void delByClientId(String clientId) {
        jpaQueryFactory.update(CLIENT)
                .set(CLIENT.delFlag, false)
                .where(CLIENT.clientId.eq(clientId))
                .execute();
    }
}

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
package com.taotao.cloud.data.jpa.configuration;

import org.hibernate.dialect.MySQL8Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy;
import org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.hibernate.cfg.AvailableSettings.DIALECT;
import static org.hibernate.cfg.AvailableSettings.IMPLICIT_NAMING_STRATEGY;
import static org.hibernate.cfg.AvailableSettings.JDBC_TIME_ZONE;
import static org.hibernate.cfg.AvailableSettings.PHYSICAL_NAMING_STRATEGY;

/**
 * Hibernate 自动配置
 *
 * @author dengtao
 * @date 2020/9/28 17:31
 * @since v1.0
 */
@EnableJpaAuditing
public class HibernateConfiguration {

	private final JpaProperties jpaProperties;

	public HibernateConfiguration(@Autowired final JpaProperties jpaProperties) {
		this.jpaProperties = jpaProperties;
	}

	@Bean
	JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		hibernateJpaVendorAdapter.setShowSql(true);
		hibernateJpaVendorAdapter.setGenerateDdl(true); //Auto creating scheme when true
		hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);//Database type
		return hibernateJpaVendorAdapter;
	}

	@Bean
	@ConditionalOnBean(DataSource.class)
	LocalContainerEntityManagerFactoryBean entityManagerFactory(
		final DataSource dataSource,
		final JpaVendorAdapter jpaVendorAdapter
		// final MultiTenantConnectionProvider multiTenantConnectionProvider,
		// final CurrentTenantIdentifierResolver currentTenantIdentifierResolver
	) {
		final Map<String, Object> newJpaProperties = new HashMap<>(jpaProperties.getProperties());
		// newJpaProperties.put(MULTI_TENANT, MultiTenancyStrategy.SCHEMA);
		// newJpaProperties.put(
		//         MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
		// newJpaProperties.put(
		//         MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);
		newJpaProperties.put(
			IMPLICIT_NAMING_STRATEGY, SpringImplicitNamingStrategy.class.getName());
		newJpaProperties.put(
			PHYSICAL_NAMING_STRATEGY, SpringPhysicalNamingStrategy.class.getName());
		newJpaProperties.put(DIALECT, MySQL8Dialect.class.getName());
		newJpaProperties.put(JDBC_TIME_ZONE, "Asia/Shanghai");

		final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean =
			new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource);
		entityManagerFactoryBean.setJpaPropertyMap(newJpaProperties);
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactoryBean.setPackagesToScan("com.taotao.cloud.*.biz.entity", "com.taotao.cloud.*.entity");
		entityManagerFactoryBean.setPersistenceUnitName("default");
		return entityManagerFactoryBean;
	}
}
